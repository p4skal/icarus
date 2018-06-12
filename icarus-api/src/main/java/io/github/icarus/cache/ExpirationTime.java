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

public class ExpirationTime implements Comparable<ExpirationTime> {

  private static final TimeUnit DEFAULT_UNIT = TimeUnit.MINUTES;

  private final long value;
  private final TimeUnit unit;

  private ExpirationTime(final long value, final TimeUnit unit) {
    this.value = value;
    this.unit = unit;
  }

  public long getValue() {
    return value;
  }

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

  public static ExpirationTime from(final Expires annotation) {
    if (annotation == null) {
      throw new NullPointerException();
    }

    return ExpirationTime.from(annotation.value(), annotation.timeUnit());
  }

  public static ExpirationTime from(final long value) {
    return ExpirationTime.from(value, ExpirationTime.DEFAULT_UNIT);
  }

  public static ExpirationTime from(final long value, final TimeUnit unit) {
    if (unit == null) {
      throw new NullPointerException();
    }

    return new ExpirationTime(value, unit);
  }
}
