package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.mirrornodeclientj.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.Operator;
import io.github.manishdait.mirrornodeclientj.data.Order;
import io.github.manishdait.mirrornodeclientj.data.AccountInfo;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class GetAccountListQuery extends Query<List<AccountInfo>> {
  private boolean balance = false;
  private Order order = Order.ASC;
  private int limit = 25;
  private PublicKey publicKey;

  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<Hbar> accountBalance;

  public GetAccountListQuery(MirrorNodeClient client) {
    super(client);
  }

  public GetAccountListQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public GetAccountListQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public GetAccountListQuery includeBalance(final boolean balance) {
    this.balance = balance;
    return this;
  }

  public GetAccountListQuery accountId(
      final @NonNull Operator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return accountId(operator, AccountId.fromString(accountId));
  }

  public GetAccountListQuery accountId(
      final @NonNull Operator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public GetAccountListQuery publicKey(final @NonNull String publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    return publicKey(PublicKey.fromString(publicKey));
  }

  public GetAccountListQuery publicKey(final @NonNull PublicKey publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    this.publicKey = publicKey;
    return this;
  }

  public GetAccountListQuery balance(final @NonNull Operator operator, final long balance) {
    Objects.requireNonNull(operator, "operator must not be null");
    if (balance < 0) {
      throw new IllegalArgumentException("limit must be greater or equal to 0");
    }

    return balance(operator, Hbar.fromTinybars(balance));
  }

  public GetAccountListQuery balance(
      final @NonNull Operator operator, final @NonNull Hbar balance) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(balance, "balance must not be null");

    this.accountBalance = new CriteriaParam<>(operator, balance);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/accounts")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("balance", String.valueOf(balance))
            .queryParam("order", order.getValue());

    if (publicKey != null) {
      request.queryParam("account.publickey", publicKey.toStringDER());
    }

    if (accountId != null) {
      request.queryParam(
          "account.id", accountId.getOperator().getValue() + ":" + accountId.getValue().toString());
    }

    if (accountBalance != null) {
      request.queryParam(
          "account.balance",
          accountBalance.getOperator().getValue()
              + ":"
              + String.valueOf(accountBalance.getValue().toTinybars()));
    }

    return request.build();
  }

  @Override
  List<AccountInfo> mapResponse(JsonNode node) {
    return JsonParserImpl.parseAccountInfos(node);
  }
}
