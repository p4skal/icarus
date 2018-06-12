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

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Optional;
import java.util.stream.Stream;
import javassist.ClassPool;
import javassist.CtClass;

final class CachingClassBytecodeReviser implements ClassFileTransformer {

  private final CachingMethodValidator methodApprover;
  private final CachingMethodBytecodeReviser methodTransformer;

  private CachingClassBytecodeReviser() {
    this(CachingMethodValidator.create(), CachingMethodBytecodeReviser.create());
  }

  private CachingClassBytecodeReviser(
      final CachingMethodValidator methodApprover,
      final CachingMethodBytecodeReviser methodTransformer) {

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
          .filter(this.methodApprover::isValid)
          .forEach(this.methodTransformer::transform);

      final byte[] bytecode = compiledClass.toBytecode();
      compiledClass.detach();

      return Optional.of(bytecode);
    } catch (final Exception transformationFailure) {
      return Optional.empty();
    }
  }

  static CachingClassBytecodeReviser create() {
    return new CachingClassBytecodeReviser();
  }

  static CachingClassBytecodeReviser create(
      final CachingMethodValidator methodApprover,
      final CachingMethodBytecodeReviser methodTransformer) {

    if (methodApprover == null || methodTransformer == null) {
      throw new NullPointerException();
    }

    return new CachingClassBytecodeReviser(methodApprover, methodTransformer);
  }
}
