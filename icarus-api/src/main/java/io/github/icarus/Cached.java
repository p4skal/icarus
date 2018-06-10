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
 * Annotation that enables the caching of a methods result value.
 *
 * @see CachedBy
 * @see Expires
 * @author Merlin Osayimwen
 * @author Paskal
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

  /**
   * Value of the {@code Cached} annotation that resembles the cache used to store the annotated
   * methods result value.
   *
   * <p>When multiple methods use the same cache to store their result value, they need to specify
   * it.
   *
   * @see io.github.icarus.Icarus#getNamedCache(String)
   * @return Name of the cache that is being used to store the result value.
   */
  String value() default "";
}
