package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.StakingReward;
import io.github.manishdait.hieromirror.query.AccountStakingRewardQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountStakingRewardQueryWrapper;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountStakingRewardQueryWrapperImpl implements AccountStakingRewardQueryWrapper {
  private final MirrorNodeClient client;
  private final String idOrAliasOrEvmAddress;

  private Order order;
  private Integer limit;
  private List<CriteriaParam<Instant>> timestamp;

  public AccountStakingRewardQueryWrapperImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");

    this.client = client;
    this.idOrAliasOrEvmAddress = idOrAliasOrEvmAddress;
  }

  @Override
  public @NonNull AccountStakingRewardQueryWrapper limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountStakingRewardQueryWrapper order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountStakingRewardQueryWrapper timestamp(
      List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull AccountStakingRewardQuery getQuery() {
    AccountStakingRewardQuery query =
        new AccountStakingRewardQuery().setAlias(idOrAliasOrEvmAddress);

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    return query;
  }

  @Override
  public @NonNull Page<StakingReward> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<StakingReward> call(@NonNull Duration timeout) {
    AccountStakingRewardQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
