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

package io.github.icarus.example.servlet.util;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;
import com.sun.net.httpserver.Authenticator.Failure;
import java.util.function.Consumer;
import javax.annotation.ParametersAreNonnullByDefault;

public final class FutureCallbacks {

  private static final Consumer<Throwable> FAILURE_HANDLER_NULL_OBJECT = ignored -> {};

  private FutureCallbacks() {}

  public static <V> FutureCallback<V> consume(
      final Consumer<V> successAction, final Consumer<Throwable> failureAction) {

    Preconditions.checkNotNull(successAction);
    Preconditions.checkNotNull(failureAction);

    return new DelegatingFutureCallback<>(successAction, failureAction);
  }

  public static <V> FutureCallback<V> ignoreFailure(final Consumer<V> successAction) {
    Preconditions.checkNotNull(successAction);

    return new DelegatingFutureCallback<V>(
        successAction, FutureCallbacks.FAILURE_HANDLER_NULL_OBJECT);
  }

  public static <V> FutureCallback<V> ignoreSuccess(final Consumer<Throwable> failureAction) {
    Preconditions.checkNotNull(failureAction);

    return new DelegatingFutureCallback<>(ignored -> {}, failureAction);
  }

  @ParametersAreNonnullByDefault
  private static final class DelegatingFutureCallback<E> implements FutureCallback<E> {

    final Consumer<E> successAction;
    final Consumer<Throwable> failureAction;

    private DelegatingFutureCallback(
        final Consumer<E> successAction, final Consumer<Throwable> failureAction) {
      this.successAction = successAction;
      this.failureAction = failureAction;
    }

    @Override
    public void onSuccess(E computedResult) {
      this.successAction.accept(computedResult);
    }

    @Override
    public void onFailure(Throwable throwable) {
      this.failureAction.accept(throwable);
    }
  }
}
