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

import io.github.icarus.example.fibonacci.FibonacciCalculator.CalculationApproach;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public final class FibonacciCalculationExample {

  private static final CalculationApproach CALCULATION_APPROACH = CalculationApproach.RECURSIVE;
  private static final IntStream CALCULATION_DEPTH_ENTRIES = IntStream.of(100, 500, 1000);

  public static void main(final String... arguments) {
    final FibonacciCalculator calculator = FibonacciCalculator.create();

    final IntFunction<String> mapperFunction =
        entry ->
            String.format(
                "The Fib of (%d) is: %d",
                entry,
                calculator.calculate(
                    entry, FibonacciCalculationExample.CALCULATION_APPROACH));

    FibonacciCalculationExample.CALCULATION_DEPTH_ENTRIES
        .mapToObj(mapperFunction)
        .forEach(System.out::println);
  }
}
