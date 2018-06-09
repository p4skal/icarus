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

package io.github.icarus.example.fibonacci;

import io.github.icarus.annotation.Cached;
import io.github.icarus.annotation.CachedBy;

final class FibonacciCalculator {

  enum CalculationApproach {
    ITERATIVE,
    RECURSIVE;
  }

  private FibonacciCalculator() {}

  @Cached
  long calculate(@CachedBy final long depth, final CalculationApproach approach) {
    return approach == CalculationApproach.ITERATIVE
        ? this.calculateIteratively(depth)
        : this.calculateRecursively(depth);
  }

  private long calculateRecursively(final long depth) {
    return 0;
  }

  private long calculateIteratively(final long depth) {
    return 0;
  }

  static FibonacciCalculator create() {
    return new FibonacciCalculator();
  }
}
