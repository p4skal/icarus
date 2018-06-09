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
import java.util.stream.DoubleStream;

/**
 * Calculates fibonacci sequences with different approaches.
 *
 * @author Merlin Osayimwen
 * @since 1.0
 * @see FibonacciCalculationExample
 */
final class FibonacciCalculator {

  private static final double GOLDEN_NUMBER = 1.618d;

  /**
   * The different approaches available for calculating a fibonacci sequence.
   *
   * <p>Using an enum instead of the strategy pattern will cause the code to have code-smells like
   * the "switch-case oop abuser" code-smell. But in this case its okay since the main purpose is to
   * show how caching is done by Icarus.
   */
  enum CalculationApproach {
    ITERATIVE,
    RECURSIVE,
    MATHEMATICAL;
  }

  private FibonacciCalculator() {}

  @Cached
  long calculate(@CachedBy final long depth, final CalculationApproach approach) {
    switch (approach) {
      case ITERATIVE:
        return this.calculateIteratively(depth);
      case RECURSIVE:
        return this.calculateRecursively(depth);
      case MATHEMATICAL:
        return this.calculateMathematically(depth);
      default:
        return 0;
    }
  }

  private long calculateRecursively(final long depth) {
    if (depth == 0 || depth == 1) {
      return depth;
    }

    return this.calculateRecursively(depth - 1) + this.calculateRecursively(depth - 2);
  }

  private long calculateIteratively(final long depth) {
    int next = 1;
    for (int iteration = 0, previous = 0; iteration < depth; iteration++) {
      final int interim = previous + next;

      previous = next;
      next = interim;
    }

    return next;
  }

  private long calculateMathematically(final long depth) {
    final double squareRoot = Math.sqrt(5);
    return DoubleStream.of(squareRoot)
        .map(result -> Math.pow(GOLDEN_NUMBER, depth))
        .map(result -> result - Math.pow(1d - GOLDEN_NUMBER, depth))
        .map(result -> Math.round(result / squareRoot))
        .boxed()
        .findAny()
        .map(Double::longValue)
        .orElse(0L);
  }

  static FibonacciCalculator create() {
    return new FibonacciCalculator();
  }
}
