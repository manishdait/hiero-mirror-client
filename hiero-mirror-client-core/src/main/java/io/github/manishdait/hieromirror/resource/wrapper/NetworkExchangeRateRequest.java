package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.ExchangeRate;
import io.github.manishdait.hieromirror.query.NetworkExchangeRateQuery;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for NetworkExchangeRateQuery. */
public interface NetworkExchangeRateRequest
    extends QueryRequest<NetworkExchangeRateQuery, Optional<ExchangeRate>> {
  /**
   * Sets the timestamp criteria filter.
   *
   * @param timestamp list of timestamp criterial params
   * @return {@code this}
   */
  @NonNull NetworkExchangeRateRequest timestamp(List<CriteriaParam<Instant>> timestamp);
}
