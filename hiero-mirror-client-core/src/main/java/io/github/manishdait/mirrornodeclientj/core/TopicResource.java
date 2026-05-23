package io.github.manishdait.mirrornodeclientj.core;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.query.TopicByIdQuery;
import org.jspecify.annotations.NonNull;

public interface TopicResource {
  default @NonNull TopicByIdQuery findById(String topicId) {
    return findById(TopicId.fromString(topicId));
  }

  @NonNull TopicByIdQuery findById(TopicId topicId);
}
