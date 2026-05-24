package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.FileId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Node;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.NetworkAddressBookQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkAddressBookRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class NetworkAddressBookRequestImpl implements NetworkAddressBookRequest {
  private final MirrorNodeClient client;

  private Order order;
  private Integer limit;
  private CriteriaParam<FileId> fileId;
  private CriteriaParam<Long> nodeId;

  public NetworkAddressBookRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkAddressBookRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull NetworkAddressBookRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull NetworkAddressBookRequest nodeId(CriteriaParam<Long> nodeId) {
    this.nodeId = nodeId;
    return this;
  }

  @Override
  public @NonNull NetworkAddressBookRequest fileId(CriteriaParam<FileId> fileId) {
    this.fileId = fileId;
    return this;
  }

  @Override
  public @NonNull NetworkAddressBookQuery getQuery() {
    NetworkAddressBookQuery query = new NetworkAddressBookQuery();

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (fileId != null) {
      query.setFileId(fileId.getOperator(), fileId.getValue());
    }

    if (nodeId != null) {
      query.setNodeId(nodeId.getOperator(), nodeId.getValue());
    }

    return query;
  }

  @Override
  public @NonNull Page<Node> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<Node> call(@NonNull Duration timeout) {
    NetworkAddressBookQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
