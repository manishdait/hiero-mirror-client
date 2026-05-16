package io.github.manishdait.mirrornodeclientj.query;

import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.Block;
import io.github.manishdait.mirrornodeclientj.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.data.Operator;
import io.github.manishdait.mirrornodeclientj.data.Order;
import io.github.manishdait.mirrornodeclientj.data.Page;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class BlockListQuery extends Query<Page<Block>> {
  private Order order = Order.DESC;
  private int limit = 25;

  private CriteriaParam<Long> blockNumber;
  private final List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public BlockListQuery(MirrorNodeClient client) {
    super(client);
  }

  public BlockListQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public BlockListQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public BlockListQuery blockNumber(final @NonNull Operator operator, final long blocNumber) {
    Objects.requireNonNull(operator, "operator must not be null");
    this.blockNumber = new CriteriaParam<>(operator, blocNumber);
    return this;
  }

  public BlockListQuery timestamp(
      final @NonNull Operator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/blocks")
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
  Page<Block> mapResponse(JsonNode node) {
    return JsonParserImpl.parseBlocks(node);
  }
}
