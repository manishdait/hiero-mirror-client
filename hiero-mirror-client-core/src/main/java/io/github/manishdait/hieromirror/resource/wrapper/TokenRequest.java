package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.TokenInfo;
import io.github.manishdait.hieromirror.query.TokenQuery;
import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for TokenQuery. */
public interface TokenRequest extends QueryRequest<TokenQuery, Optional<TokenInfo>> {
  /**
   * Sets the timestamp criteria param.
   *
   * @param timestamp the {@link CriteriaParam} for timestamp
   * @return {@code this}
   */
  @NonNull TokenRequest timestamp(CriteriaParam<Instant> timestamp);
}
