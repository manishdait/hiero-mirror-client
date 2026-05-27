package io.github.manishdait.hieromirror.resource;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.resource.wrapper.TopicRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

/** Access point for executing topic related queries. */
public interface TopicResource {
  /** Prepares a query request to fetch single topic by id. */
  default @NonNull TopicRequest findById(final @NonNull String topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    return findById(TopicId.fromString(topicId));
  }

  /** Prepares a query request to fetch single topic by id. */
  @NonNull TopicRequest findById(final @NonNull TopicId topicId);
}
