/*
 * Copyright 2018 Icarus Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.icarus.internal;

import com.google.common.base.Preconditions;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Optional;
import java.util.stream.Stream;
import javassist.ClassPool;
import javassist.CtClass;

final class InternalClassFileTransformer implements ClassFileTransformer {

  private final InternalMethodApprover methodApprover;
  private final InternalMethodTransformer methodTransformer;

  private InternalClassFileTransformer() {
    this(InternalMethodApprover.create(), InternalMethodTransformer.create());
  }

  private InternalClassFileTransformer(
      final InternalMethodApprover methodApprover,
      final InternalMethodTransformer methodTransformer) {

    this.methodApprover = methodApprover;
    this.methodTransformer = methodTransformer;
  }

  @Override
  public byte[] transform(
      final ClassLoader loader,
      final String className,
      final Class<?> classBeingRedefined,
      final ProtectionDomain protectionDomain,
      final byte[] classfileBuffer) {

    return this.tryTransform(className.replace('/', '.')).orElse(classfileBuffer);
  }

  private Optional<byte[]> tryTransform(final String className) {
    try {
      ClassPool pool = ClassPool.getDefault();
      CtClass compiledClass = pool.get(className);

      Stream.of(compiledClass.getDeclaredMethods())
          .filter(this.methodApprover::approves)
          .forEach(this.methodTransformer::transform);

      final byte[] bytecode = compiledClass.toBytecode();
      compiledClass.detach();

      return Optional.of(bytecode);
    } catch (final Exception transformationFailure) {
      return Optional.empty();
    }
  }

  static InternalClassFileTransformer create() {
    return new InternalClassFileTransformer();
  }

  static InternalClassFileTransformer create(
      final InternalMethodApprover methodApprover,
      final InternalMethodTransformer methodTransformer) {

    Preconditions.checkNotNull(methodApprover);
    Preconditions.checkNotNull(methodTransformer);

    return new InternalClassFileTransformer(methodApprover, methodTransformer);
  }
}
