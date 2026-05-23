package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NetworkFee;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class NetworkFeeQuery extends Query<Optional<NetworkFee>> {
  private Order order = Order.ASC;
  private List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public NetworkFeeQuery() {}

  public Order getOrder() {
    return order;
  }

  public NetworkFeeQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  public NetworkFeeQuery setTimestamp(final @NonNull List<CriteriaParam<Instant>> timestamps) {
    Objects.requireNonNull(timestamps, "timestamps must not be null");
    this.timestamps = new ArrayList<>(timestamps);
    return this;
  }

  public NetworkFeeQuery clearTimestamp() {
    this.timestamps = new ArrayList<>();
    return this;
  }

  public NetworkFeeQuery addTimestamp(
      final @NonNull QueryOperator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/network/fees")
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
  Optional<NetworkFee> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseNetworkFee(node);
  }
}
