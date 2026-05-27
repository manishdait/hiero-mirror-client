package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.Topic;
import io.github.manishdait.hieromirror.query.TopicQuery;
import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for TopicQuery. */
public interface TopicRequest extends QueryRequest<TopicQuery, Optional<Topic>> {
  /**
   * Gets the TopicMessageList request for the topic
   *
   * @return instance of {@link TopicMessageListRequest}
   */
  @NonNull TopicMessageListRequest messages();

  /**
   * Gets the TopicMessage request for the topic
   *
   * @return instance of {@link TopicMessageBySequenceRequest}
   */
  @NonNull TopicMessageByTimestampRequest messageByTimestamp(final @NonNull Instant timestamp);

  /**
   * Gets the TopicMessage request for the topic
   *
   * @return instance of {@link TopicMessageBySequenceRequest}
   */
  @NonNull TopicMessageBySequenceRequest messageBySequence(final @NonNull Long sequence);
}
