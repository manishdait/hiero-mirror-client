package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import java.time.Instant;
import java.util.List;

public interface BlockListQueryWrapper {
  BlockListQueryWrapper order(Order order);

  BlockListQueryWrapper limit(Integer integer);

  BlockListQueryWrapper blockNumber(CriteriaParam<Long> blockNumber);

  BlockListQueryWrapper timestamps(List<CriteriaParam<Instant>> timestamps);
}
