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

/**
 * Query to get a single topic message for the given topic id and either sequence number or
 * consensusTimestamp.
 */
public class TopicMessageQuery extends Query<Optional<TopicMessage>> {
  private TopicId topicId;
  @Nullable private Long sequenceNumber;
  @Nullable private String timestamp;

  /** Constructor. */
  public TopicMessageQuery() {}

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
  public TopicMessageQuery setTopicId(final @NonNull String topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    return setTopicId(TopicId.fromString(topicId));
  }

  /**
   * Sets the topicId.
   *
   * @param topicId the target {@link TopicId} instance
   * @return {@code this}
   */
  public TopicMessageQuery setTopicId(final @NonNull TopicId topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    this.topicId = topicId;
    return this;
  }

  /**
   * Gets the timestamp.
   *
   * @return the timestamp
   */
  public @Nullable String getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the timestamp.
   *
   * @param timestamp the timestamp
   * @return {@code this}
   */
  public TopicMessageQuery setTimestamp(final @Nullable String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Gets the sequenceNumber.
   *
   * @return the sequenceNumber
   */
  public @Nullable Long getSequenceNumber() {
    return sequenceNumber;
  }

  /**
   * Sets the sequenceNumber.
   *
   * @param sequenceNumber the sequenceNumber
   * @return {@code this}
   */
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
