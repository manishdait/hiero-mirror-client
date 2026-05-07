package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.JsonParserImpl;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.Operator;
import io.github.manishdait.mirrornodeclientj.Order;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.data.StakingReward;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetStackingRewardQuery extends Query<List<StakingReward>> {
  private final AccountId accountId;

  private Order order = Order.DESC;
  private int limit = 25;
  private final List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public GetStackingRewardQuery(MirrorNodeClient client, AccountId accountId) {
    super(client);
    this.accountId = accountId;
  }

  public GetStackingRewardQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public GetStackingRewardQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

    public GetStackingRewardQuery timestamp(
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
        .url(this.client.getBaseUrl() + "/api/v1/accounts/" + accountId + "/rewards")
        .method("GET")
        .queryParam("limit", String.valueOf(limit))
        .queryParam("order", order.getValue());

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
  List<StakingReward> mapResponse(JsonNode node) {
    return JsonParserImpl.parseStakingRewards(node);
  }
}
