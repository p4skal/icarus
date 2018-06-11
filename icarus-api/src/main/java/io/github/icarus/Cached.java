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
 * <p>A method annotated with the {@code @Cached} annotation will cache its return value and use it
 * when it is invoked with the same arguments. Methods without arguments will simply always return
 * the result that has only been calculated once. There are several additional annotations which
 * slightly modify the time and/or way how results are cached. But simply annotating a method is
 * enough in order for {@code Icarus} to do the other work.
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
   * <p>There are multiple reasons why the name may need to be defined.
   *
   * <ol>
   *   <li>The developer wants to access the cache manually at some time and needs it name for that.
   *   <li>Two or more methods want to use the same cache to store their result values.
   * </ol>
   *
   * <p>Especially Overloaded methods are often calculating the same result. Lets make an example:
   * Method {@code a(int)} and method {@code a(int,Logger)} are both calculating some value the one
   * is logging something while calculating it. At the we still want to reuse a result if there is
   * one.
   *
   * <p>Custom cache names are only useful when:
   *
   * <ol>
   *   <li>They use the {@code @CachedBy} annotation respectively to produce the same hash value for
   *       equal arguments
   *   <li>They need to have the same {@code TTL}. One may not use the {@code @Expires} annotation
   *       if the other doesn't and if both do, they need to have the same value
   * </ol>
   *
   * Otherwise all methods that use this cache might fail caching and the software has some serious
   * problems. Just know, that this is one of the more advanced parts of {@code Icarus} even though
   * if it seems to be completely simple.
   *
   * <p>By default, the name of a methods cache is dependent on the methods name. It may not be
   * humanly readable and when the {@code value} is not defined.
   *
   * @return Name of the cache that is being used to store the result value.
   */
  String value() default "";
}
