package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.BlockListQuery;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for BlockListQuery. */
public interface BlockListRequest extends QueryRequest<BlockListQuery, Page<Block>> {
  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull BlockListRequest order(Order order);

  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull BlockListRequest limit(int limit);

  /**
   * Sets the blockNumber criteria filter.
   *
   * @param blockNumber the {@link CriteriaParam} for block number
   * @return {@code this}
   */
  @NonNull BlockListRequest blockNumber(CriteriaParam<Long> blockNumber);

  /**
   * Sets the timestamp criteria filter.
   *
   * @param timestamp list of timestamp criterial params
   * @return {@code this}
   */
  @NonNull BlockListRequest timestamps(List<CriteriaParam<Instant>> timestamp);
}
