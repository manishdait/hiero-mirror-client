package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.ScheduleInfo;
import io.github.manishdait.hieromirror.query.ScheduleListQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for ScheduleListQuery. */
public interface ScheduleListRequest extends QueryRequest<ScheduleListQuery, Page<ScheduleInfo>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull ScheduleListRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull ScheduleListRequest order(Order order);

  /**
   * Sets an accountId criteria filter.
   *
   * @param accountId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull ScheduleListRequest accountId(CriteriaParam<AccountId> accountId);

  /**
   * Sets an scheduleId criteria filter.
   *
   * @param scheduleId the {@link CriteriaParam} for scheduleId
   * @return {@code this}
   */
  @NonNull ScheduleListRequest scheduleId(CriteriaParam<ScheduleId> scheduleId);
}
