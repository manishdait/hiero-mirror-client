package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.query.TopicByIdQuery;
import org.jspecify.annotations.NonNull;

public interface TopicResource {
  default @NonNull TopicByIdQuery findById(String topicId) {
    return findById(TopicId.fromString(topicId));
  }

  @NonNull TopicByIdQuery findById(TopicId topicId);
}
