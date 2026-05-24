package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.FileId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Node;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get the network address book nodes. */
public class NetworkAddressBookQuery extends Query<Page<Node>> {
  private Order order = Order.ASC;
  private int limit = 25;

  @Nullable private CriteriaParam<FileId> fileId;
  @Nullable private CriteriaParam<Long> nodeId;

  /** Constructor. */
  public NetworkAddressBookQuery() {}

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
  public NetworkAddressBookQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  /**
   * Gets the maximum number of items to be retrieved. Defaults to {@code 25}.
   *
   * @return maximum number of records
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the maximum number of items to return. Must be within range: 1 to 100 inclusive.
   *
   * @param limit maximum items to return
   * @return {@code this}
   * @throws IllegalArgumentException if limit is outside range [1, 100]
   */
  public NetworkAddressBookQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }

    this.limit = limit;
    return this;
  }

  /**
   * Gets the fileId criteria filter.
   *
   * @return the fileId criteria
   */
  public @Nullable CriteriaParam<FileId> getFileId() {
    return fileId;
  }

  /**
   * Sets the fileId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param fileId the string representation of fileId
   * @return {@code this}
   */
  public NetworkAddressBookQuery setFileId(
      final @NonNull QueryOperator operator, final @NonNull String fileId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(fileId, "fileId must not be null");

    return setFileId(operator, FileId.fromString(fileId));
  }

  /**
   * Sets the fileId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param fileId the target {@link FileId} instance
   * @return {@code this}
   */
  public NetworkAddressBookQuery setFileId(
      final @NonNull QueryOperator operator, final @NonNull FileId fileId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(fileId, "fileId must not be null");

    this.fileId = new CriteriaParam<>(operator, fileId);
    return this;
  }

  /**
   * Gets the nodeId criteria filter.
   *
   * @return the nodeId criteria
   */
  public @Nullable CriteriaParam<Long> getNodeId() {
    return nodeId;
  }

  /**
   * Sets the nodeId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param nodeId the nodeId
   * @return {@code this}
   */
  public NetworkAddressBookQuery setNodeId(
      final @NonNull QueryOperator operator, final long nodeId) {
    Objects.requireNonNull(operator, "operator must not be null");
    this.nodeId = new CriteriaParam<>(operator, nodeId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/network/nodes")
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
  Page<Node> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseNodes(node);
  }
}
