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

package io.github.icarus;

import io.github.icarus.cache.IcarusCache;

public final class Icarus {

  private Icarus() throws IllegalAccessException {
    throw new IllegalAccessException(
        String.format("The %s class may not be constructed", this.getClass().getName()));
  }

  public static IcarusCache getNamedCache(final String name) {
    return null;
  }
}
