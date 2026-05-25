package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NftAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountNftAllowanceQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountNftAllowanceRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountNftAllowanceRequestImpl implements AccountNftAllowanceRequest {
  private final MirrorNodeClient client;
  private final String idOrAliasOrEvmAddress;

  private Order order;
  private Integer limit;
  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<TokenId> tokenId;
  private Boolean owner;

  public AccountNftAllowanceRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");

    this.client = client;
    this.idOrAliasOrEvmAddress = idOrAliasOrEvmAddress;
  }

  @Override
  public @NonNull AccountNftAllowanceRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountNftAllowanceRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountNftAllowanceRequest spenderId(CriteriaParam<AccountId> spenderId) {
    this.accountId = spenderId;
    return this;
  }

  @Override
  public @NonNull AccountNftAllowanceRequest tokenId(CriteriaParam<TokenId> tokenId) {
    this.tokenId = tokenId;
    return this;
  }

  @Override
  public @NonNull AccountNftAllowanceRequest owner(boolean value) {
    this.owner = value;
    return this;
  }

  @Override
  public @NonNull AccountNftAllowanceQuery buildQuery() {
    AccountNftAllowanceQuery query = new AccountNftAllowanceQuery().setAlias(idOrAliasOrEvmAddress);

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (accountId != null) {
      query.setSpenderId(accountId.getOperator(), accountId.getValue());
    }

    if (tokenId != null) {
      query.setTokenId(tokenId.getOperator(), tokenId.getValue());
    }

    if (owner != null) {
      query.setOwner(true);
    }

    return query;
  }

  @Override
  public @NonNull Page<NftAllowance> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<NftAllowance> call(@NonNull Duration timeout) {
    AccountNftAllowanceQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
