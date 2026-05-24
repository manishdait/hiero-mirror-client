package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.Topic;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

/** Query to get the topic details for the given topic ID. */
public class TopicQuery extends Query<Optional<Topic>> {
  private TopicId topicId;

  /** Constructor. */
  public TopicQuery() {}

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
  public TopicQuery setTopicId(final @NonNull String topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    return setTopicId(TopicId.fromString(topicId));
  }

  /**
   * Sets the topicId.
   *
   * @param topicId the target {@link TopicId} instance
   * @return {@code this}
   */
  public TopicQuery setTopicId(final @NonNull TopicId topicId) {
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
            .url(client.getBaseUrl() + "/api/v1/topics/" + topicId)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<Topic> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseTopic(node);
  }
}
