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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * The {@code Expires} annotation introduces a {@code time to live} to result values of methods
 * annotated with the {@code @Cached} annotation.
 *
 * <p>When a result value should be cached only for a specified amount of time, this annotation is
 * needed. Since the cache is created by the framework and not the user, {@code Icarus} needs to now
 * exactly how long to store cache results. When this annotation is absent whatsoever, the results
 * will be cached without being removed because of their "age".
 *
 * @author Merlin Osayimwen
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Expires {

  /**
   * This value is the {@code TTL} of the {@code cached result value}.
   *
   * <p>The {@link Expires#timeUnit()} value is the matching unit to the {@code long} returned by
   * this function.
   *
   * @return Amount of time, in the specified {@linkplain Expires#timeUnit() unit}, that the {@code
   *     result} of a {@code caching} method is cached.
   */
  long value();

  /**
   * Unit of the {@link Expires#value() time to live}.
   *
   * @return The TTLs unit.
   */
  TimeUnit timeUnit() default TimeUnit.SECONDS;
}
