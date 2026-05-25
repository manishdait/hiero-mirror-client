package io.github.manishdait.hieromirror.resource.wrapper;

import java.time.Duration;
import org.jspecify.annotations.NonNull;

public interface QueryRequest<Q, R> {
  /** Return the query from the request. */
  @NonNull Q buildQuery();

  /** Execute the request. */
  @NonNull R call();

  /** Execute request with timeout. */
  @NonNull R call(final @NonNull Duration timeout);
}
