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

/**
 * Marks one or more parameters that are used as keys when caching their methods result.
 *
 * <p>When a parameterized method is already marked with the {@code Cached} annotation and it has
 * parameters which should be ignored when mapping the result value to the cache, this annotation is
 * needed. The absence of the {@code CachedBy} annotation will result in every parameter of the
 * method to be used.
 *
 * @see Cached
 * @since 1.0
 * @author Merlin Osayimwen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CachedBy {

  String[] value() default {};
}
