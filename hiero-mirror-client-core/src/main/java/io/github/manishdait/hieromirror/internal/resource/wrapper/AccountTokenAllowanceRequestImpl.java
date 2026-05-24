package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.TokenAllowance;
import io.github.manishdait.hieromirror.query.AccountTokenAllowanceQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountTokenAllowanceRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountTokenAllowanceRequestImpl implements AccountTokenAllowanceRequest {
  private final MirrorNodeClient client;
  private final String idOrAliasOrEvmAddress;

  private Order order;
  private Integer limit;
  private CriteriaParam<AccountId> spenderId;
  private CriteriaParam<TokenId> tokenId;

  public AccountTokenAllowanceRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");

    this.client = client;
    this.idOrAliasOrEvmAddress = idOrAliasOrEvmAddress;
  }

  @Override
  public @NonNull AccountTokenAllowanceRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountTokenAllowanceRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountTokenAllowanceRequest spenderId(CriteriaParam<AccountId> spenderId) {
    this.spenderId = spenderId;
    return this;
  }

  @Override
  public @NonNull AccountTokenAllowanceRequest tokenId(CriteriaParam<TokenId> tokenId) {
    this.tokenId = tokenId;
    return this;
  }

  @Override
  public @NonNull AccountTokenAllowanceQuery getQuery() {
    AccountTokenAllowanceQuery query =
        new AccountTokenAllowanceQuery().setAlias(idOrAliasOrEvmAddress);

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (spenderId != null) {
      query.setSpenderId(spenderId.getOperator(), spenderId.getValue());
    }

    if (tokenId != null) {
      query.setTokenId(tokenId.getOperator(), tokenId.getValue());
    }

    return query;
  }

  @Override
  public @NonNull Page<TokenAllowance> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<TokenAllowance> call(@NonNull Duration timeout) {
    AccountTokenAllowanceQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
