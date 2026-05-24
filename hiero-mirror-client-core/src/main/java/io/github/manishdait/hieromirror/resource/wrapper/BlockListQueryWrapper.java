package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.NonNull;

public interface BlockListQueryWrapper {
  @NonNull BlockListQueryWrapper order(Order order);

  @NonNull BlockListQueryWrapper limit(Integer integer);

  @NonNull BlockListQueryWrapper blockNumber(CriteriaParam<Long> blockNumber);

  @NonNull BlockListQueryWrapper timestamps(List<CriteriaParam<Instant>> timestamps);
}
