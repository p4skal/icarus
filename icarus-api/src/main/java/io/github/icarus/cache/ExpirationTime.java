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

import io.github.icarus.Expires;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Represents the time an {@code IcarusCacheElement} is cached in a {@code IcarusCache}.
 *
 * <p>To prevent {@code primitive Obsession}, the {@code expirationTime} is represented as its own
 * type. Caches will use them two determine on when elements may be removed. Instances of the class
 * may be compared to others and the common object methods have been overwritten so that they are
 * representative and in order to make the {@code ExpirationTime} a real value object.
 *
 * @author Merlin Osayimwen
 * @since 1.0
 * @see IcarusCache
 * @see AbstractIcarusCacheFactory
 */
public class ExpirationTime implements Comparable<ExpirationTime> {

  /** The default TimeUnit which is used as a fallback value. */
  private static final TimeUnit DEFAULT_UNIT = TimeUnit.MINUTES;

  /** The amount of time in the set {@code unit}. */
  private final long value;

  /** The {@code unit} in which the {@code value} is defined. */
  private final TimeUnit unit;

  /**
   * Parameterized constructor that initializes an {@code ExpirationTime} with all arguments
   * available.
   *
   * @param value The amount of time.
   * @param unit The Unit of the {@code amount}.
   */
  private ExpirationTime(final long value, final TimeUnit unit) {
    this.value = value;
    this.unit = unit;
  }

  /**
   * The amount of time in the {@code unit}.
   *
   * @return Amount of time.
   */
  public long getValue() {
    return value;
  }

  /**
   * The {@code unit} in which the {@code value} is defined.
   *
   * @return unit in which the value is defined.
   */
  public TimeUnit getUnit() {
    return unit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value, this.unit);
  }

  @Override
  public String toString() {
    return String.format(
        "%s{value=%d,unit=%s}", this.getClass().getSimpleName(), this.value, this.unit.name());
  }

  @Override
  public boolean equals(final Object checkTarget) {
    if (this == checkTarget) {
      return true;
    }

    if (checkTarget == null) {
      return false;
    }

    if (!(checkTarget instanceof ExpirationTime)) {
      return false;
    }

    final ExpirationTime otherTime = (ExpirationTime) checkTarget;
    return this.value == otherTime.value && this.unit.equals(otherTime.unit);
  }

  @Override
  public int compareTo(ExpirationTime comparisonTarget) {
    if (comparisonTarget == null) {
      throw new NullPointerException();
    }

    final long nanos = this.unit.toNanos(this.value);
    final long otherNanos = comparisonTarget.unit.toNanos(this.value);
    return Long.compareUnsigned(nanos, otherNanos);
  }

  /**
   * Creates the {@code ExpirationTime} instance from its related annotation.
   *
   * @param annotation Annotation that the class is constructed from.
   * @return Created {@code ExpirationTime} instance.
   */
  public static ExpirationTime from(final Expires annotation) {
    if (annotation == null) {
      throw new NullPointerException();
    }

    return ExpirationTime.from(annotation.value(), annotation.timeUnit());
  }

  /**
   * Creates a new instance of the class with the given {@code value}.
   *
   * @param value The amount of time.
   * @return Created instance of the class with the given {@code value}.
   */
  public static ExpirationTime from(final long value) {
    return ExpirationTime.from(value, ExpirationTime.DEFAULT_UNIT);
  }

  /**
   * Creates a new instance of the class with the given {@code value} and {@code unit}.
   *
   * @param value The amount of time.
   * @param unit The {@code values} unit.
   * @return Created instance of the class with the given {@code value} and {@code unit}.
   */
  public static ExpirationTime from(final long value, final TimeUnit unit) {
    if (unit == null) {
      throw new NullPointerException();
    }

    return new ExpirationTime(value, unit);
  }
}
