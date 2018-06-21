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

package io.github.icarus.cache;

import java.util.Optional;

/**
 * Abstraction of the intern caches.
 *
 * @author Merlin Osayimwen
 * @since 1.0
 * @see AbstractIcarusCacheFactory
 * @see IcarusCacheElement
 */
public interface IcarusCache {

  void put(final long key, final IcarusCacheElement value);

  Optional<IcarusCacheElement> get(final int key);

  Optional<IcarusCacheElement> remove(final int key);
}
