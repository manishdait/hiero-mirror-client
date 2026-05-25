package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.RegisteredNode;
import io.github.manishdait.hieromirror.model.RegisteredServiceType;
import io.github.manishdait.hieromirror.query.NetworkRegisteredAddressBookQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkRegisteredAddressBookRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class NetworkRegisteredAddressBookRequestImpl
    implements NetworkRegisteredAddressBookRequest {
  private final MirrorNodeClient client;

  private Integer limit;
  private Order order;
  private CriteriaParam<Long> nodeId;
  private RegisteredServiceType type;

  public NetworkRegisteredAddressBookRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkRegisteredAddressBookRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull NetworkRegisteredAddressBookRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull NetworkRegisteredAddressBookRequest registeredNodeId(CriteriaParam<Long> nodeId) {
    this.nodeId = nodeId;
    return this;
  }

  @Override
  public @NonNull NetworkRegisteredAddressBookRequest type(RegisteredServiceType type) {
    this.type = type;
    return this;
  }

  @Override
  public @NonNull NetworkRegisteredAddressBookQuery getQuery() {
    NetworkRegisteredAddressBookQuery query = new NetworkRegisteredAddressBookQuery();

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (nodeId != null) {
      query.setRegisteredNodeId(nodeId.getOperator(), nodeId.getValue());
    }

    if (type != null) {
      query.setRegisteredServiceType(type);
    }

    return query;
  }

  @Override
  public @NonNull Page<RegisteredNode> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<RegisteredNode> call(@NonNull Duration timeout) {
    NetworkRegisteredAddressBookQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
