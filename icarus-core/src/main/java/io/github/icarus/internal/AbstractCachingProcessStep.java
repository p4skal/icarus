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
import java.util.Optional;

abstract class AbstractCachingProcessStep {

  private AbstractCachingProcessStep nextStep;

  AbstractCachingProcessStep() {
    this(null);
  }

  AbstractCachingProcessStep getNextStep() {
    return this.nextStep;
  }

  void setNextStep(AbstractCachingProcessStep nextStep) {
    this.nextStep = nextStep;
  }

  private AbstractCachingProcessStep(final AbstractCachingProcessStep nextStep) {
    this.nextStep = nextStep;
  }

  abstract Optional<Object> applyTo(final Object entry, final EntryDirection direction);
}
