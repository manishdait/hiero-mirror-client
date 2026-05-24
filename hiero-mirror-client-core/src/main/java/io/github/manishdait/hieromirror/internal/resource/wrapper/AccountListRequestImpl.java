package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountListQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountListRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountListRequestImpl implements AccountListRequest {
  private final MirrorNodeClient client;

  private Order order;
  private Integer limit;
  private Boolean includeBalance;
  private PublicKey publicKey;
  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<Hbar> balance;

  public AccountListRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull AccountListRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountListRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountListRequest includeBalance(boolean includeBalance) {
    this.includeBalance = includeBalance;
    return this;
  }

  @Override
  public @NonNull AccountListRequest publicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  @Override
  public @NonNull AccountListRequest accountId(CriteriaParam<AccountId> accountId) {
    this.accountId = accountId;
    return this;
  }

  @Override
  public @NonNull AccountListRequest balance(CriteriaParam<Hbar> balance) {
    this.balance = balance;
    return this;
  }

  @Override
  public @NonNull AccountListQuery getQuery() {
    AccountListQuery query = new AccountListQuery();
    if (order != null) {
      query.setOrder(order);
    }

    if (limit != null) {
      query.setLimit(limit);
    }

    if (includeBalance != null) {
      query.setIncludeBalance(includeBalance);
    }

    if (publicKey != null) {
      query.setPublicKey(publicKey);
    }

    if (accountId != null) {
      query.setAccountId(accountId.getOperator(), accountId.getValue());
    }

    if (balance != null) {
      query.setBalance(balance.getOperator(), balance.getValue());
    }

    return query;
  }

  @Override
  public @NonNull Page<AccountInfo> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<AccountInfo> call(@NonNull Duration timeout) {
    AccountListQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
