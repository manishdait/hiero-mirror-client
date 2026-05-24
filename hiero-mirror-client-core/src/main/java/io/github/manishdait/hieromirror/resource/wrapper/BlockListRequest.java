package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.BlockListQuery;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.NonNull;

public interface BlockListRequest extends QueryRequest<BlockListQuery, Page<Block>> {
  @NonNull BlockListRequest order(Order order);

  @NonNull BlockListRequest limit(int limit);

  @NonNull BlockListRequest blockNumber(CriteriaParam<Long> blockNumber);

  @NonNull BlockListRequest timestamps(List<CriteriaParam<Instant>> timestamps);
}
