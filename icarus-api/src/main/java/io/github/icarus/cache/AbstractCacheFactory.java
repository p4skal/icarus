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
import java.util.concurrent.Executor;

/**
 * Abstract-Factory used to create instances of a {@code Cache}.
 *
 * @author Merlin Osayimwen
 * @see Cache
 * @see ExpirationTime
 */
public abstract class AbstractCacheFactory {

  protected ExpirationTime expirationTime;
  protected Executor delegatedExecutor;

  protected AbstractCacheFactory(
      final ExpirationTime expirationTime, final Executor delegatedExecutor) {

    this.expirationTime = expirationTime;
    this.delegatedExecutor = delegatedExecutor;
  }

  public final void setExecutor(final Executor executor) {
    this.delegatedExecutor = executor;
  }

  public final void setExpirationTime(final ExpirationTime expirationTime) {
    this.expirationTime = expirationTime;
  }

  public abstract Cache getInstance(final String name);

  @Override
  public int hashCode() {
    return Objects.hash(this.expirationTime, this.delegatedExecutor);
  }

  @Override
  public String toString() {
    return String.format(
        "%s{expirationTime=%s,executor=%s}",
        this.getClass().getSimpleName(),
        this.expirationTime.toString(),
        this.delegatedExecutor.toString());
  }

  @Override
  public boolean equals(final Object checkTarget) {
    if (this == checkTarget) {
      return true;
    }

    if (checkTarget == null) {
      return false;
    }

    if (!(checkTarget instanceof AbstractCacheFactory)) {
      return false;
    }

    final AbstractCacheFactory otherFactory = (AbstractCacheFactory) checkTarget;
    return this.expirationTime.equals(otherFactory.expirationTime)
        && this.delegatedExecutor.equals(otherFactory.delegatedExecutor);
  }
}
