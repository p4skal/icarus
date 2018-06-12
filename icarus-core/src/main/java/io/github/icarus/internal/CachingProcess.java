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

package io.github.icarus.internal;

import io.github.icarus.module.EntryDirection;
import java.util.Objects;
import java.util.Optional;

final class CachingProcess {

  private final AbstractStructuredCachingProcessStep headStep;

  private CachingProcess(final AbstractStructuredCachingProcessStep headStep) {
    this.headStep = headStep;
  }

  Result runForIncomingEntry(final Object originalEntry) {
    return this.runDirectional(originalEntry, EntryDirection.INCOMING);
  }

  Result runForOutgoingEntry(final Object originalEntry) {
    return this.runDirectional(originalEntry, EntryDirection.OUTGOING);
  }

  private Result runDirectional(final Object originalEntry, final EntryDirection direction) {
    // Iterates the steps and appliesx to entry to all of them.
    AbstractStructuredCachingProcessStep currentStep = this.headStep;
    Object currentEntry = originalEntry;
    while (currentStep != null) {
      final Optional<Object> stepResult = currentStep.applyTo(currentEntry, direction);
      if (!stepResult.isPresent()) {
        return Result.filtered();
      }

      // The optional has been misused in this example.
      currentEntry = stepResult.get();
      currentStep = currentStep.getNextStep();
    }

    return Result.proceeded(originalEntry);
  }

  static final class Result {
    private final boolean filtered;
    private final Object processedObject;

    private Result(final boolean filtered, final Object processedObject) {
      this.filtered = filtered;
      this.processedObject = processedObject;
    }

    boolean hasBeenFiltered() {
      return this.filtered;
    }

    Object getProcessedObject() {
      return this.processedObject;
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.filtered, this.processedObject);
    }

    @Override
    public boolean equals(final Object checkTarget) {
      if (this == checkTarget) {
        return true;
      }

      if (checkTarget == null) {
        return false;
      }

      if (!(checkTarget instanceof CachingProcess)) {
        return false;
      }

      final Result otherResult = (Result) checkTarget;
      return this.filtered == otherResult.filtered
          && Objects.equals(this.processedObject, otherResult.processedObject);
    }

    @Override
    public String toString() {
      return String.format(
          "%s{filtered=%s,result=%s}",
          this.getClass().getSimpleName(),
          this.filtered,
          this.processedObject == null ? "null" : this.processedObject.toString());
    }

    private static Result proceeded(final Object result) {
      return new Result(false, result);
    }

    private static Result filtered() {
      return new Result(true, false);
    }
  }
}
