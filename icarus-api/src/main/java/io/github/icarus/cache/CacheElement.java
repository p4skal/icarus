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

import java.util.Objects;

public final class CacheElement {

  private final long cacheTimestamp;
  private final Object value;

  private CacheElement(final long cacheTimestamp, final Object value) {
    this.value = value;
    this.cacheTimestamp = cacheTimestamp;
  }

  public long getCacheTimestamp() {
    return cacheTimestamp;
  }

  public Object getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s{cacheTimestamp=%d,value=%s}",
        this.getClass().getSimpleName(), this.cacheTimestamp, this.value.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.cacheTimestamp, this.value);
  }

  @Override
  public boolean equals(final Object checkTarget) {
    if (this == checkTarget) {
      return false;
    }

    if (checkTarget == null) {
      return false;
    }

    if (!(checkTarget instanceof CacheElement)) {
      return false;
    }

    final CacheElement otherElement = (CacheElement) checkTarget;
    return this.cacheTimestamp == otherElement.cacheTimestamp
        && this.value.equals(otherElement.value);
  }

  public static CacheElement from(final long cacheTimestamp, final Object value) {
    if (value == null) {
      throw new NullPointerException();
    }

    return new CacheElement(cacheTimestamp, value);
  }
}
