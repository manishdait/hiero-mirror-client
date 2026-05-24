package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.CryptoAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountCryptoAllowanceQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountCryptoAllowanceRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountCryptoAllowanceRequestImpl implements AccountCryptoAllowanceRequest {
  private final MirrorNodeClient client;
  private final String idOrAliasOrEvmAddress;

  private Order order;
  private Integer limit;
  private CriteriaParam<AccountId> spenderId;

  public AccountCryptoAllowanceRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");

    this.client = client;
    this.idOrAliasOrEvmAddress = idOrAliasOrEvmAddress;
  }

  @Override
  public @NonNull AccountCryptoAllowanceRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountCryptoAllowanceRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountCryptoAllowanceRequest spenderId(CriteriaParam<AccountId> spenderId) {
    this.spenderId = spenderId;
    return this;
  }

  @Override
  public @NonNull AccountCryptoAllowanceQuery getQuery() {
    AccountCryptoAllowanceQuery query =
        new AccountCryptoAllowanceQuery().setAlias(idOrAliasOrEvmAddress);

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (spenderId != null) {
      query.setSpenderId(spenderId.getOperator(), spenderId.getValue());
    }

    return query;
  }

  @Override
  public @NonNull Page<CryptoAllowance> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<CryptoAllowance> call(@NonNull Duration timeout) {
    AccountCryptoAllowanceQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
