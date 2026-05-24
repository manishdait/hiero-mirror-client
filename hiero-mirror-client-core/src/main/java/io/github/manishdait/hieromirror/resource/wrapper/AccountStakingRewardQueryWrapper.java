package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.StakingReward;
import io.github.manishdait.hieromirror.query.AccountStakingRewardQuery;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.NonNull;

public interface AccountStakingRewardQueryWrapper
    extends QueryWrapper<AccountStakingRewardQuery, Page<StakingReward>> {
  @NonNull AccountStakingRewardQueryWrapper limit(int limit);

  @NonNull AccountStakingRewardQueryWrapper order(Order order);

  @NonNull AccountStakingRewardQueryWrapper timestamp(List<CriteriaParam<Instant>> timestamp);
}
