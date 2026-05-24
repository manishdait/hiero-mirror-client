package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.AccountQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountCryptoAllowanceQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountNftAllowanceQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountNftListQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountStakingRewardQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountTokenAllowanceQueryWrapper;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class AccountQueryWrapperImpl implements AccountQueryWrapper {
  private final MirrorNodeClient client;
  private final String idOrAliasOrEvmAddress;

  private Order order;
  private Integer limit;
  private Boolean includeTransaction;
  private TransactionType transactionType;
  private List<CriteriaParam<Instant>> timestamp;

  public AccountQueryWrapperImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");

    this.client = client;
    this.idOrAliasOrEvmAddress = idOrAliasOrEvmAddress;
  }

  @Override
  public @NonNull AccountQueryWrapper order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountQueryWrapper limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountQueryWrapper includeTransaction(Boolean includeTransaction) {
    this.includeTransaction = includeTransaction;
    return this;
  }

  @Override
  public @NonNull AccountQueryWrapper transactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  @Override
  public @NonNull AccountQueryWrapper timestamp(List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull AccountCryptoAllowanceQueryWrapper cryptoAllowance() {
    return new AccountCryptoAllowanceQueryWrapperImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountTokenAllowanceQueryWrapper tokenAllowance() {
    return new AccountTokenAllowanceQueryWrapperImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountNftAllowanceQueryWrapper nftAllowance() {
    return new AccountNftAllowanceQueryWrapperImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountNftListQueryWrapper nftList() {
    return new AccountNftListQueryWrapperImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountStakingRewardQueryWrapper stakingReward() {
    return new AccountStakingRewardQueryWrapperImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountQuery getQuery() {
    AccountQuery query = new AccountQuery().setAlias(idOrAliasOrEvmAddress);

    if (order != null) {
      query.setOrder(order);
    }

    if (limit != null) {
      query.setLimit(limit);
    }

    if (includeTransaction != null) {
      query.setIncludeTransaction(includeTransaction);
    }

    if (transactionType != null) {
      query.setTransactionType(transactionType);
    }

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    return query;
  }

  @Override
  public @NonNull Optional<AccountInfo> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<AccountInfo> call(@NonNull Duration timeout) {
    AccountQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
