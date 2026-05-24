package io.github.manishdait.hieromirror.resource.wrapper;

import java.time.Duration;
import org.jspecify.annotations.NonNull;

public interface QueryRequest<Q, R> {
  /** Return the query from the wrapper. */
  @NonNull Q getQuery();

  /** Execute the query. */
  @NonNull R call();

  /** Execute query with timeout. */
  @NonNull R call(final @NonNull Duration timeout);
}
