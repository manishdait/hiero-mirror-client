package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.query.GetTopicByIdQuery;
import org.jspecify.annotations.NonNull;

public interface TopicResource {
  default @NonNull GetTopicByIdQuery findById(String topicId) {
    return findById(TopicId.fromString(topicId));
  }

  @NonNull GetTopicByIdQuery findById(TopicId topicId);
}
