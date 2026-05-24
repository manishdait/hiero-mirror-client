package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NetworkFee;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.NetworkFeeQuery;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public interface NetworkFeeRequest extends QueryRequest<NetworkFeeQuery, Optional<NetworkFee>> {
  @NonNull NetworkFeeRequest order(Order order);

  @NonNull NetworkFeeRequest timestamp(List<CriteriaParam<Instant>> timestamp);
}
