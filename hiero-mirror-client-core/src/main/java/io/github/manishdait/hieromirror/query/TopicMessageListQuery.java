package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.TopicMessage;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get list of topic messages for the given topic id. */
public final class TopicMessageListQuery extends Query<Page<TopicMessage>> {
  private TopicId topicId;

  private Order order = Order.ASC;
  private int limit = 25;
  private Long sequenceNumber;

  private List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  /** Constructor. */
  public TopicMessageListQuery() {}

  /**
   * Gets the topicId.
   *
   * @return the topicId
   */
  public TopicId getTopicId() {
    return topicId;
  }

  /**
   * Sets the topicId.
   *
   * @param topicId the string representation of topicId
   * @return {@code this}
   */
  public TopicMessageListQuery setTopicId(final @NonNull String topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    return setTopicId(TopicId.fromString(topicId));
  }

  /**
   * Sets the topicId.
   *
   * @param topicId the target {@link TopicId} instance
   * @return {@code this}
   */
  public TopicMessageListQuery setTopicId(final @NonNull TopicId topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    this.topicId = topicId;
    return this;
  }

  /**
   * Gets the sorting order for the query items. Defaults to {@code desc}.
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
  public TopicMessageListQuery setOrder(final @NonNull Order order) {
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
  public TopicMessageListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  /**
   * Get the sequence number filter.
   *
   * @return the sequenceNumber filter
   */
  public @Nullable Long getSequenceNumber() {
    return this.sequenceNumber;
  }

  /**
   * Set the sequenceNumber filter.
   *
   * @param sequenceNumber the sequence number
   * @return {@code this}
   */
  public TopicMessageListQuery setSequenceNumber(@Nullable Long sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
    return this;
  }

  /**
   * Gets the timestamp criteria filter.
   *
   * @return list of timestamp criteria.
   */
  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  /**
   * Sets the timestamp criteria filter using a list of timestamps.
   *
   * @param timestamps list of timestamp criterial params
   * @return {@code this}
   */
  public TopicMessageListQuery setTimestamps(
      final @NonNull List<CriteriaParam<Instant>> timestamps) {
    Objects.requireNonNull(timestamps, "timestamps must not be null");
    this.timestamps = new ArrayList<>(timestamps);
    return this;
  }

  /**
   * Clears all timestamp criteria filter.
   *
   * @return {@code this}
   */
  public TopicMessageListQuery clearTimestamps() {
    this.timestamps = new ArrayList<>();
    return this;
  }

  /**
   * Adds a single timestamp criteria to the filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param timestamp the timestamp to add
   * @return {@code this}
   */
  public TopicMessageListQuery addTimestamp(
      final @NonNull QueryOperator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (topicId == null) {
      throw new IllegalStateException("topicId must be set before executing query");
    }
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/topics/" + topicId + "/messages")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (sequenceNumber != null) {
      request.queryParam("sequencenumber", String.valueOf(sequenceNumber));
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
  Page<TopicMessage> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseTopicMessages(node);
  }
}
