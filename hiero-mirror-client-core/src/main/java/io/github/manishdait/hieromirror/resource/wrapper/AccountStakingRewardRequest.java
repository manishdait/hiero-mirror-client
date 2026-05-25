package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.StakingReward;
import io.github.manishdait.hieromirror.query.AccountStakingRewardQuery;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for AccountStakingRewardQuery. */
public interface AccountStakingRewardRequest
    extends QueryRequest<AccountStakingRewardQuery, Page<StakingReward>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull AccountStakingRewardRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull AccountStakingRewardRequest order(Order order);

  /**
   * Sets the timestamp criteria filter.
   *
   * @param timestamp list of timestamp criterial params
   * @return {@code this}
   */
  @NonNull AccountStakingRewardRequest timestamp(List<CriteriaParam<Instant>> timestamp);
}
