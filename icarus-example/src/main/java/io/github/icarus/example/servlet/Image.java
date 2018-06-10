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

package io.github.icarus.example.servlet;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Objects;

final class Image {

  private static final String FALLBACK_CONTENT_TYPE = "unknown";
  private static final byte[] EMPTY_CONTENT = new byte[0];

  private final String token;
  private final String contentType;
  private final byte[] content;

  private Image(final String token, final String contentType, final byte[] content) {
    this.token = token;
    this.contentType = contentType;
    this.content = content.clone();
  }

  String getToken() {
    return this.token;
  }

  String getContentType() {
    return contentType;
  }

  byte[] getContent() {
    return content.clone();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("token", this.token)
        .add("content-type", this.contentType)
        .add("content-size", this.content.length)
        .toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.token, this.contentType, Arrays.hashCode(this.content));
  }

  @Override
  public boolean equals(final Object checkTarget) {
    if (this == checkTarget) {
      return true;
    }

    if (checkTarget == null) {
      return false;
    }

    if (!(checkTarget instanceof Image)) {
      return false;
    }

    final Image otherImage = (Image) checkTarget;
    return this.token.equals(otherImage.token)
        && this.contentType.equals(otherImage.contentType)
        && Arrays.equals(this.content, otherImage.content);
  }

  static Image createEmpty(final String token) {
    return Image.createEmpty(token, Image.FALLBACK_CONTENT_TYPE);
  }

  static Image createEmpty(final String token, final String contentType) {
    return Image.create(token, contentType, Image.EMPTY_CONTENT);
  }

  static Image create(final String token, final String contentType, final byte[] content) {
    Preconditions.checkNotNull(token);
    Preconditions.checkNotNull(contentType);
    Preconditions.checkNotNull(content);

    return new Image(token, contentType, content);
  }
}
