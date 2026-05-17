package io.github.manishdait.mirrornodeclientj.core.query;

import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import io.github.manishdait.mirrornodeclientj.core.data.Order;
import io.github.manishdait.mirrornodeclientj.core.data.Page;
import io.github.manishdait.mirrornodeclientj.core.data.RegisteredNode;
import io.github.manishdait.mirrornodeclientj.core.data.RegisteredServiceType;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class RegisteredAddressBookQuery extends Query<Page<RegisteredNode>> {
  private Order order = Order.ASC;
  private int limit = 25;
  private RegisteredServiceType type;

  private CriteriaParam<Long> registeredNodeId;

  public RegisteredAddressBookQuery(MirrorNodeClient client) {
    super(client);
  }

  public Order getOrder() {
    return order;
  }

  public int getLimit() {
    return limit;
  }

  public RegisteredServiceType getType() {
    return type;
  }

  public CriteriaParam<Long> getRegisteredNodeId() {
    return registeredNodeId;
  }

  public RegisteredAddressBookQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public RegisteredAddressBookQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public RegisteredAddressBookQuery type(final @NonNull RegisteredServiceType type) {
    Objects.requireNonNull(type, "type must not be null");
    this.type = type;
    return this;
  }

  public RegisteredAddressBookQuery registeredNodeId(
      final @NonNull Operator operator, final long nodeId) {
    Objects.requireNonNull(operator, "operator must not be null");
    this.registeredNodeId = new CriteriaParam<>(operator, nodeId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/network/registered-nodes")
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
  Page<RegisteredNode> mapResponse(JsonNode node) {
    return JsonParserImpl.parseRegisteredNodes(node);
  }
}
