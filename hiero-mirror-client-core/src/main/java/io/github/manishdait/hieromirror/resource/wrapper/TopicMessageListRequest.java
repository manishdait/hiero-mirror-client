package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.TopicMessage;
import io.github.manishdait.hieromirror.query.TopicMessageListQuery;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for TopicMessageListQuery. */
public interface TopicMessageListRequest
    extends QueryRequest<TopicMessageListQuery, Page<TopicMessage>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull TopicMessageListRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull TopicMessageListRequest order(Order order);

  /**
   * Sets the sequenceNumber filter.
   *
   * @param value the sequenceNumber
   * @return {@code this}
   */
  @NonNull TopicMessageListRequest sequenceNumber(long value);

  /**
   * Sets the timestamp criteria filter.
   *
   * @param timestamp list of timestamp criterial params
   * @return {@code this}
   */
  @NonNull TopicMessageListRequest timestamp(List<CriteriaParam<Instant>> timestamp);
}
