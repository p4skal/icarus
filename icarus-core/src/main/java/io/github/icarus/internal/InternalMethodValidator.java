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

import io.github.icarus.Cached;
import java.util.stream.Stream;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

final class InternalMethodValidator {

  private static final Stream<CtClass> DISALLOWED_RETURN_TYPES = Stream.of(CtClass.voidType);
  private static final Class<?> NEEDED_ANNOTATION = Cached.class;

  private InternalMethodValidator() {}

  boolean isValid(final CtMethod method) {
    return this.approveAnnotations(method) && this.approveReturnType(method);
  }

  private boolean approveAnnotations(final CtMethod method) {
    return method.hasAnnotation(InternalMethodValidator.NEEDED_ANNOTATION);
  }

  private boolean approveReturnType(final CtMethod method) {
    try {
      final CtClass returnType = method.getReturnType();
      return InternalMethodValidator.DISALLOWED_RETURN_TYPES.anyMatch(returnType::equals);
    } catch (final NotFoundException findingFailure) {
      return false;
    }
  }

  static InternalMethodValidator create() {
    return new InternalMethodValidator();
  }
}
