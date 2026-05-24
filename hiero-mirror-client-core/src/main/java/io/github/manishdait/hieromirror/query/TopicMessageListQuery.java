package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.TopicMessage;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

/** Query to get list of topic messages for the given topic id. */
public final class TopicMessageListQuery extends Query<Page<TopicMessage>> {
  private TopicId topicId;

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

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (topicId == null) {
      throw new IllegalStateException("topicId must be set before executing query");
    }
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/topics/" + topicId + "/messages")
            .method("GET");

    return request.build();
  }

  @Override
  Page<TopicMessage> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseTopicMessages(node);
  }
}
