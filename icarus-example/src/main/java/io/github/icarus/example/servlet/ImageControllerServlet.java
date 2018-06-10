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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import io.github.icarus.annotation.Cached;
import io.github.icarus.annotation.CachedBy;
import io.github.icarus.annotation.Expires;

import io.github.icarus.example.servlet.util.FutureCallbacks;

public class ImageControllerServlet extends HttpServlet {

  private static final int IMAGE_CACHE_TIME = 0;

  private final AtomicLong totalRequests;

  private final Logger logger;
  private final Executor executor;
  private final ImageRepository repository;

  private ImageControllerServlet(
      final Logger logger, final Executor executor, final ImageRepository repository) {

    this.logger = logger;
    this.executor = executor;
    this.repository = repository;
    this.totalRequests = new AtomicLong();
  }

  @Override
  @Cached
  @Expires(IMAGE_CACHE_TIME)
  protected void doGet(
      @CachedBy({"getParameter(\"token\")"}) final HttpServletRequest request,
      final HttpServletResponse response)
      throws ServletException, IOException {

    this.totalRequests.incrementAndGet();

    final PrintWriter writer = response.getWriter();
    final String token = request.getParameter("token");
    final ListenableFuture<Image> selectedImage = this.repository.selectImageByToken(token);

    final Consumer<Image> successAction = image -> this.tryPrintImageAsync(image, response);
    final Consumer<Throwable> failureAction =
        failure -> writer.printf("No Image with the token \"%s\" has been found", token);

    Futures.addCallback(
        selectedImage, FutureCallbacks.consume(successAction, failureAction), executor);
  }

  private void tryPrintImageAsync(final Image image, final HttpServletResponse response) {
    executor.execute(() -> this.tryPrintImage(image, response));
  }

  private void tryPrintImage(final Image image, final HttpServletResponse response) {
    try {
      this.printImage(image, response);
    } catch (final IOException printingFailure) {
      if (!logger.isLoggable(Level.INFO)) {
        return;
      }

      logger.log(Level.INFO, "Failed to print image.", printingFailure);
    }
  }

  private void printImage(final Image image, final HttpServletResponse response)
      throws IOException {

    final Writer output = response.getWriter();
  }

  static ImageControllerServlet create(
      final Logger logger, final Executor executor, final ImageRepository repository) {

    Preconditions.checkNotNull(logger);
    Preconditions.checkNotNull(executor);
    Preconditions.checkNotNull(repository);

    return new ImageControllerServlet(logger, executor, repository);
  }
}
