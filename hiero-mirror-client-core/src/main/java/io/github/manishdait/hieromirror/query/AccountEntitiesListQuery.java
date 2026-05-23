package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Operator;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public final class AccountEntitiesListQuery extends Query<Page<AccountInfo>> {
  private Order order = Order.ASC;
  private int limit = 25;
  private boolean includeBalance = true;
  private PublicKey publicKey;

  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<Hbar> balance;

  public AccountEntitiesListQuery() {}

  public Order getOrder() {
    return order;
  }

  public AccountEntitiesListQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public AccountEntitiesListQuery limit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }

    this.limit = limit;
    return this;
  }

  public boolean getIncludeBalance() {
    return includeBalance;
  }

  public AccountEntitiesListQuery setIncludeBalance(final boolean value) {
    this.includeBalance = value;
    return this;
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public AccountEntitiesListQuery setPublicKey(final @NonNull String publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    return setPublicKey(PublicKey.fromString(publicKey));
  }

  public AccountEntitiesListQuery setPublicKey(final @NonNull PublicKey publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    this.publicKey = publicKey;
    return this;
  }

  public CriteriaParam<AccountId> getAccountId() {
    return accountId;
  }

  public AccountEntitiesListQuery setAccountId(
    final @NonNull Operator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    return setAccountId(operator, AccountId.fromString(accountId));
  }

  public AccountEntitiesListQuery setAccountId(
    final @NonNull Operator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public CriteriaParam<Hbar> getBalance() {
    return balance;
  }

  public AccountEntitiesListQuery setBalance(final @NonNull Operator operator, final long balance) {
    Objects.requireNonNull(operator, "operator must not be null");
    if (balance < 0) {
      throw new IllegalArgumentException("limit must be greater or equal to 0");
    }

    return setBalance(operator, Hbar.fromTinybars(balance));
  }

  public AccountEntitiesListQuery setBalance(final @NonNull Operator operator, final @NonNull Hbar balance) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(balance, "balance must not be null");

    this.balance = new CriteriaParam<>(operator, balance);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/accounts")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("balance", String.valueOf(includeBalance))
            .queryParam("order", order.getValue());

    if (publicKey != null) {
      request.queryParam("account.publickey", publicKey.toStringDER());
    }

    if (accountId != null) {
      request.queryParam(
          "account.id", accountId.getOperator().getValue() + ":" + accountId.getValue().toString());
    }

    if (balance != null) {
      request.queryParam(
          "account.balance",
          balance.getOperator().getValue()
              + ":"
              + String.valueOf(balance.getValue().toTinybars()));
    }

    return request.build();
  }

  @Override
  Page<AccountInfo> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseAccountInfos(node);
  }
}
