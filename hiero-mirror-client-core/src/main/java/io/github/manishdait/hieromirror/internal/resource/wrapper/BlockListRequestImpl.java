package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.BlockListQuery;
import io.github.manishdait.hieromirror.resource.wrapper.BlockListRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class BlockListRequestImpl implements BlockListRequest {
  private final MirrorNodeClient client;

  private Order order;
  private Integer limit;
  private CriteriaParam<Long> blockNumber;
  private List<CriteriaParam<Instant>> timestamp;

  public BlockListRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull BlockListRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull BlockListRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull BlockListRequest blockNumber(CriteriaParam<Long> blockNumber) {
    this.blockNumber = blockNumber;
    return this;
  }

  @Override
  public @NonNull BlockListRequest timestamps(List<CriteriaParam<Instant>> timestamps) {
    this.timestamp = timestamps;
    return this;
  }

  @Override
  public @NonNull BlockListQuery buildQuery() {
    BlockListQuery query = new BlockListQuery();

    if (order != null) {
      query.setOrder(order);
    }

    if (limit != null) {
      query.setLimit(limit);
    }

    if (blockNumber != null) {
      query.setBlockNumber(blockNumber.getOperator(), blockNumber.getValue());
    }

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    return query;
  }

  @Override
  public @NonNull Page<Block> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<Block> call(@NonNull Duration timeout) {
    BlockListQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
