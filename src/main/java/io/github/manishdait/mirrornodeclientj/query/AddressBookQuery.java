package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.FileId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.data.Node;
import io.github.manishdait.mirrornodeclientj.data.Operator;
import io.github.manishdait.mirrornodeclientj.data.Order;
import io.github.manishdait.mirrornodeclientj.data.Page;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class AddressBookQuery extends Query<Page<Node>> {
  private Order order = Order.ASC;
  private int limit = 25;

  private CriteriaParam<FileId> fileId;
  private CriteriaParam<Long> nodeId;

  public AddressBookQuery(MirrorNodeClient client) {
    super(client);
  }

  public AddressBookQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public AddressBookQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public AddressBookQuery fileId(final @NonNull Operator operator, final @NonNull String fileId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(fileId, "fileId must not be null");

    return fileId(operator, FileId.fromString(fileId));
  }

  public AddressBookQuery fileId(final @NonNull Operator operator, final @NonNull FileId fileId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(fileId, "fileId must not be null");

    this.fileId = new CriteriaParam<>(operator, fileId);
    return this;
  }

  public AddressBookQuery nodeId(final @NonNull Operator operator, final long nodeId) {
    Objects.requireNonNull(operator, "operator must not be null");
    this.nodeId = new CriteriaParam<>(operator, nodeId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/network/nodes")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (fileId != null) {
      request.queryParam(
          "file.id", fileId.getOperator().getValue() + ":" + fileId.getValue().toString());
    }

    if (nodeId != null) {
      request.queryParam("node.id", nodeId.getOperator().getValue() + ":" + nodeId.getValue());
    }

    return request.build();
  }

  @Override
  Page<Node> mapResponse(JsonNode node) {
    return JsonParserImpl.parseNodes(node);
  }
}
