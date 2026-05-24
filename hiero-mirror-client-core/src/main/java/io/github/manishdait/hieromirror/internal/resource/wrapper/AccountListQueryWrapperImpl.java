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
import io.github.manishdait.hieromirror.resource.wrapper.AccountListQueryWrapper;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountListQueryWrapperImpl implements AccountListQueryWrapper {
  private final MirrorNodeClient client;

  private Order order;
  private Integer limit;
  private Boolean includeBalance;
  private PublicKey publicKey;
  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<Hbar> balance;

  public AccountListQueryWrapperImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull AccountListQueryWrapper order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountListQueryWrapper limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountListQueryWrapper includeBalance(boolean includeBalance) {
    this.includeBalance = includeBalance;
    return this;
  }

  @Override
  public @NonNull AccountListQueryWrapper publicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  @Override
  public @NonNull AccountListQueryWrapper accountId(CriteriaParam<AccountId> accountId) {
    this.accountId = accountId;
    return this;
  }

  @Override
  public @NonNull AccountListQueryWrapper balance(CriteriaParam<Hbar> balance) {
    this.balance = balance;
    return this;
  }

  @Override
  public @NonNull Page<AccountInfo> call() {
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

    return query.execute(client);
  }
}
