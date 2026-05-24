package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.BlockListQuery;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.NonNull;

public interface BlockListQueryWrapper extends QueryWrapper<BlockListQuery, Page<Block>> {
  @NonNull BlockListQueryWrapper order(Order order);

  @NonNull BlockListQueryWrapper limit(int limit);

  @NonNull BlockListQueryWrapper blockNumber(CriteriaParam<Long> blockNumber);

  @NonNull BlockListQueryWrapper timestamps(List<CriteriaParam<Instant>> timestamps);
}
