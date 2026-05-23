package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

public class BlockListQuery extends Query<Page<Block>> {
  private Order order = Order.DESC;
  private int limit = 25;

  @Nullable private CriteriaParam<Long> blockNumber;
  private final List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public BlockListQuery() {}

  public Order getOrder() {
    return order;
  }

  public BlockListQuery getOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public BlockListQuery setLimit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public @Nullable CriteriaParam<Long> getBlockNumber() {
    return blockNumber;
  }

  public BlockListQuery setBlockNumber(
      final @NonNull QueryOperator operator, final long blocNumber) {
    Objects.requireNonNull(operator, "operator must not be null");
    this.blockNumber = new CriteriaParam<>(operator, blocNumber);
    return this;
  }

  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  public BlockListQuery setTimestamp(
      final @NonNull QueryOperator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not ne null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/blocks")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (blockNumber != null) {
      request.queryParam(
          "block.number", blockNumber.getOperator().getValue() + ":" + blockNumber.getValue());
    }

    for (CriteriaParam<Instant> timestamp : timestamps) {
      request.queryParam(
          "timestamp",
          timestamp.getOperator().getValue()
              + ":"
              + timestamp.getValue().getEpochSecond()
              + "."
              + timestamp.getValue().getNano());
    }

    return request.build();
  }

  @Override
  Page<Block> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseBlocks(node);
  }
}
