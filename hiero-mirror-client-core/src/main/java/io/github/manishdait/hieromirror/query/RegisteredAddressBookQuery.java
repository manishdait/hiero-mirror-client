package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.RegisteredNode;
import io.github.manishdait.hieromirror.model.RegisteredServiceType;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class RegisteredAddressBookQuery extends Query<Page<RegisteredNode>> {
  private Order order = Order.ASC;
  private int limit = 25;
  private RegisteredServiceType type;

  private CriteriaParam<Long> registeredNodeId;

  public RegisteredAddressBookQuery() {}

  public Order getOrder() {
    return order;
  }

  public RegisteredAddressBookQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public RegisteredAddressBookQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  public RegisteredServiceType getType() {
    return type;
  }

  public RegisteredAddressBookQuery setType(final @NonNull RegisteredServiceType type) {
    Objects.requireNonNull(type, "type must not be null");
    this.type = type;
    return this;
  }

  public CriteriaParam<Long> getRegisteredNodeId() {
    return registeredNodeId;
  }

  public RegisteredAddressBookQuery setRegisteredNodeId(
      final @NonNull QueryOperator operator, final long nodeId) {
    Objects.requireNonNull(operator, "operator must not be null");
    this.registeredNodeId = new CriteriaParam<>(operator, nodeId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/network/registered-nodes")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (type != null) {
      request.queryParam("type", type.getValue());
    }

    if (registeredNodeId != null) {
      request.queryParam(
          "registerednode.id",
          registeredNodeId.getOperator().getValue() + ":" + registeredNodeId.getValue());
    }

    return request.build();
  }

  @Override
  Page<RegisteredNode> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseRegisteredNodes(node);
  }
}
