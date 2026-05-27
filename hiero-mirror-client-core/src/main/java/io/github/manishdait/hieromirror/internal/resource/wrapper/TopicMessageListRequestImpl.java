package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.TopicMessage;
import io.github.manishdait.hieromirror.query.TopicMessageListQuery;
import io.github.manishdait.hieromirror.resource.wrapper.TopicMessageListRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class TopicMessageListRequestImpl implements TopicMessageListRequest {
  private final MirrorNodeClient client;
  private final TopicId topicId;

  private Integer limit;
  private Order order;
  private Long sequenceNumber;
  private List<CriteriaParam<Instant>> timestamp;

  public TopicMessageListRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull TopicId topicId) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(topicId, "topicId must not be null");

    this.client = client;
    this.topicId = topicId;
  }

  @Override
  public @NonNull TopicMessageListRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull TopicMessageListRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull TopicMessageListRequest sequenceNumber(long value) {
    this.sequenceNumber = value;
    return this;
  }

  @Override
  public @NonNull TopicMessageListRequest timestamp(List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull TopicMessageListQuery buildQuery() {
    TopicMessageListQuery query = new TopicMessageListQuery().setTopicId(topicId);

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    if (sequenceNumber != null) {
      query.setSequenceNumber(sequenceNumber);
    }

    return query;
  }

  @Override
  public @NonNull Page<TopicMessage> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<TopicMessage> call(@NonNull Duration timeout) {
    TopicMessageListQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
