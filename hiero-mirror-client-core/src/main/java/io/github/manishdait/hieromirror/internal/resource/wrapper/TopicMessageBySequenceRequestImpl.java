package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.TopicMessage;
import io.github.manishdait.hieromirror.query.TopicMessageQuery;
import io.github.manishdait.hieromirror.resource.wrapper.TopicMessageBySequenceRequest;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class TopicMessageBySequenceRequestImpl implements TopicMessageBySequenceRequest {
  private final MirrorNodeClient client;
  private final TopicId topicId;
  private final Long sequenceNumber;

  public TopicMessageBySequenceRequestImpl(
      final @NonNull MirrorNodeClient client,
      final @NonNull TopicId topicId,
      final @NonNull Long sequenceNumber) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(topicId, "topicId must not be null");
    Objects.requireNonNull(sequenceNumber, "sequenceNumber not be null");

    this.client = client;
    this.topicId = topicId;
    this.sequenceNumber = sequenceNumber;
  }

  @Override
  public @NonNull TopicMessageQuery buildQuery() {
    return new TopicMessageQuery().setTopicId(topicId).setSequenceNumber(sequenceNumber);
  }

  @Override
  public @NonNull Optional<TopicMessage> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<TopicMessage> call(@NonNull Duration timeout) {
    TopicMessageQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
