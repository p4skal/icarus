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

import io.github.icarus.Icarus;
import io.github.icarus.cache.IcarusCache;
import java.util.Collection;
import javassist.CannotCompileException;
import javassist.CtMethod;

final class CachingMethodBytecodeReviser {

  private CachingMethodBytecodeReviser() {}

  void transform(final CtMethod method) {
    try {
      method.insertBefore("{if()}");
    } catch (final CannotCompileException exception) {
    }
  }

  static CachingMethodBytecodeReviser create() {
    return new CachingMethodBytecodeReviser();
  }

  private static void methodBody(String name,Collection<String> parameters) {
    final long hash = 0;
    final IcarusCache cache = Icarus.getNamedCache("name");
    cache.
  }

}
