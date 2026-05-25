package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NetworkFee;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.NetworkFeeQuery;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for NetworkFeeQuery. */
public interface NetworkFeeRequest extends QueryRequest<NetworkFeeQuery, Optional<NetworkFee>> {
  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull NetworkFeeRequest order(Order order);

  /**
   * Sets the timestamp criteria filter.
   *
   * @param timestamp list of timestamp criterial params
   * @return {@code this}
   */
  @NonNull NetworkFeeRequest timestamp(List<CriteriaParam<Instant>> timestamp);
}
