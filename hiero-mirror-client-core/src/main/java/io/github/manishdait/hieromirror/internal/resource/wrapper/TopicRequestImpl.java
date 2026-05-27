package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Topic;
import io.github.manishdait.hieromirror.query.TopicQuery;
import io.github.manishdait.hieromirror.resource.wrapper.TopicMessageBySequenceRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TopicMessageByTimestampRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TopicMessageListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TopicRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class TopicRequestImpl implements TopicRequest {
  private final MirrorNodeClient client;
  private final TopicId topicId;

  public TopicRequestImpl(final @NonNull MirrorNodeClient client, final @NonNull TopicId topicId) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(topicId, "topicId must not be null");

    this.client = client;
    this.topicId = topicId;
  }

  @Override
  public @NonNull TopicMessageListRequest messages() {
    return new TopicMessageListRequestImpl(client, topicId);
  }

  @Override
  public @NonNull TopicMessageByTimestampRequest messageByTimestamp(@NonNull Instant timestamp) {
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    return new TopicMessageByTimestampRequestImpl(client, topicId, timestamp);
  }

  @Override
  public @NonNull TopicMessageBySequenceRequest messageBySequence(@NonNull Long sequence) {
    Objects.requireNonNull(sequence, "sequence must not be null");
    return new TopicMessageBySequenceRequestImpl(client, topicId, sequence);
  }

  @Override
  public @NonNull TopicQuery buildQuery() {
    return new TopicQuery().setTopicId(topicId);
  }

  @Override
  public @NonNull Optional<Topic> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<Topic> call(@NonNull Duration timeout) {
    TopicQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
