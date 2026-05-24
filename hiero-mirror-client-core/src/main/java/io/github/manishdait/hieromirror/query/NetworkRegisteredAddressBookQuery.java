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
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get the list of registered nodes. */
public final class NetworkRegisteredAddressBookQuery extends Query<Page<RegisteredNode>> {
  private Order order = Order.ASC;
  private int limit = 25;

  @Nullable private RegisteredServiceType type;

  @Nullable private CriteriaParam<Long> registeredNodeId;

  /** Constructor. */
  public NetworkRegisteredAddressBookQuery() {}

  /**
   * Gets the sorting order for the query items. Defaults to {@code asc}.
   *
   * @return the {@link Order}
   */
  public Order getOrder() {
    return order;
  }

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  public NetworkRegisteredAddressBookQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  /**
   * Gets the maximum number of transactions to be retrieved. Defaults to {@code 25}.
   *
   * @return maximum number of records
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the maximum number of transactions to return. Must be within range: 1 to 100 inclusive.
   *
   * @param limit maximum items to return
   * @return {@code this}
   * @throws IllegalArgumentException if limit is outside range [1, 100]
   */
  public NetworkRegisteredAddressBookQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  /**
   * Gets the RegisterServiceType to ge fetch.
   *
   * @return the RegisterServiceType
   */
  public RegisteredServiceType getRegisterServiceType() {
    return type;
  }

  /**
   * Sets the RegisterServiceType to ge fetch.
   *
   * @param type the registerServiceType
   * @return {@code this}
   */
  public NetworkRegisteredAddressBookQuery setRegisteredServiceType(
      final @NonNull RegisteredServiceType type) {
    Objects.requireNonNull(type, "type must not be null");
    this.type = type;
    return this;
  }

  /**
   * Gets the registeredNodeId criteria filter.
   *
   * @return the registeredNodeId criteria
   */
  public @Nullable CriteriaParam<Long> getRegisteredNodeId() {
    return registeredNodeId;
  }

  /**
   * Sets the registeredNodeId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param nodeId the registeredNodeId
   * @return {@code this}
   */
  public NetworkRegisteredAddressBookQuery setRegisteredNodeId(
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
