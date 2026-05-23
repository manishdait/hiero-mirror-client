package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.TopicMessage;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

public class TopicMessageQuery extends Query<Optional<TopicMessage>> {
  private TopicId topicId;
  @Nullable private Long sequenceNumber;
  @Nullable private String timestamp;

  public TopicMessageQuery() {}

  public TopicId getTopicId() {
    return topicId;
  }

  public TopicMessageQuery setTopicId(final @NonNull String topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    return setTopicId(TopicId.fromString(topicId));
  }

  public TopicMessageQuery setTopicId(final @NonNull TopicId topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    this.topicId = topicId;
    return this;
  }

  public @Nullable String getTimestamp() {
    return timestamp;
  }

  public TopicMessageQuery setTimestamp(final @Nullable String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public @Nullable Long getSequenceNumber() {
    return sequenceNumber;
  }

  public TopicMessageQuery setSequenceNumber(final @Nullable Long sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (sequenceNumber == null && timestamp == null) {
      throw new IllegalStateException(
          "either sequenceNumber or timestamp must be set before executing query");
    }

    if (sequenceNumber != null && timestamp != null) {
      throw new IllegalStateException("only one of sequenceNumber or timestamp must be set");
    }

    MirrorNodeRequest.Builder request = MirrorNodeRequest.newBuilder().method("GET");

    if (timestamp != null) {
      request.url(client.getBaseUrl() + "/api/v1/topics/" + topicId + "/messages/" + timestamp);
    } else {
      request.url(
          client.getBaseUrl() + "/api/v1/topics/" + topicId + "/messages/" + sequenceNumber);
    }

    return request.build();
  }

  @Override
  Optional<TopicMessage> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseTopicMessage(node);
  }
}
