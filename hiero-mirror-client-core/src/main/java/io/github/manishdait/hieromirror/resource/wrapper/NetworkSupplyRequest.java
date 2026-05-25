package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NetworkSupply;
import io.github.manishdait.hieromirror.query.NetworkSupplyQuery;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for NetworkSupplyQuery. */
public interface NetworkSupplyRequest
    extends QueryRequest<NetworkSupplyQuery, Optional<NetworkSupply>> {
  /**
   * Sets the timestamp criteria filter.
   *
   * @param timestamp list of timestamp criterial params
   * @return {@code this}
   */
  @NonNull NetworkSupplyRequest timestamp(List<CriteriaParam<Instant>> timestamp);
}
