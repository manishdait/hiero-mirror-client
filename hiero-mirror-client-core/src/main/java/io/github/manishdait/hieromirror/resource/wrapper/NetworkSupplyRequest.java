package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NetworkSupply;
import io.github.manishdait.hieromirror.query.NetworkSupplyQuery;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public interface NetworkSupplyRequest
    extends QueryRequest<NetworkSupplyQuery, Optional<NetworkSupply>> {
  @NonNull NetworkSupplyRequest timestamp(List<CriteriaParam<Instant>> timestamp);
}
