package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.AccountQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountCryptoAllowanceRequest;
import io.github.manishdait.hieromirror.resource.wrapper.AccountNftAllowanceRequest;
import io.github.manishdait.hieromirror.resource.wrapper.AccountNftListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.AccountRequest;
import io.github.manishdait.hieromirror.resource.wrapper.AccountStakingRewardRequest;
import io.github.manishdait.hieromirror.resource.wrapper.AccountTokenAllowanceRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class AccountRequestImpl implements AccountRequest {
  private final MirrorNodeClient client;
  private final String idOrAliasOrEvmAddress;

  private Order order;
  private Integer limit;
  private Boolean includeTransaction;
  private TransactionType transactionType;
  private List<CriteriaParam<Instant>> timestamp;

  public AccountRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");

    this.client = client;
    this.idOrAliasOrEvmAddress = idOrAliasOrEvmAddress;
  }

  @Override
  public @NonNull AccountRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountRequest limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountRequest includeTransaction(Boolean includeTransaction) {
    this.includeTransaction = includeTransaction;
    return this;
  }

  @Override
  public @NonNull AccountRequest transactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  @Override
  public @NonNull AccountRequest timestamp(List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull AccountCryptoAllowanceRequest cryptoAllowance() {
    return new AccountCryptoAllowanceRequestImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountTokenAllowanceRequest tokenAllowance() {
    return new AccountTokenAllowanceRequestImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountNftAllowanceRequest nftAllowance() {
    return new AccountNftAllowanceRequestImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountNftListRequest nftList() {
    return new AccountNftListRequestImpl(client, idOrAliasOrEvmAddress);
  }

  @Override
  public @NonNull AccountStakingRewardRequest stakingReward() {
    return new AccountStakingRewardRequestImpl(client, idOrAliasOrEvmAddress);
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
