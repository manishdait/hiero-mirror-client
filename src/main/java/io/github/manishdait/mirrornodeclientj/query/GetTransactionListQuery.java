package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.BalanceModifier;
import io.github.manishdait.mirrornodeclientj.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.Operator;
import io.github.manishdait.mirrornodeclientj.Order;
import io.github.manishdait.mirrornodeclientj.TransactionResult;
import io.github.manishdait.mirrornodeclientj.TransactionType;
import io.github.manishdait.mirrornodeclientj.data.Transaction;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class GetTransactionListQuery extends Query<List<Transaction>> {
  private Order order = Order.DESC;
  private int limit = 25;
  private TransactionType transactionType;
  private TransactionResult transactionResult;
  private BalanceModifier balanceModifier;

  private CriteriaParam<AccountId> accountId;
  private final List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public GetTransactionListQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public GetTransactionListQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public GetTransactionListQuery transactionType(final @NonNull TransactionType type) {
    Objects.requireNonNull(type, "type must not be null");
    this.transactionType = type;
    return this;
  }

  public GetTransactionListQuery result(final @NonNull TransactionResult result) {
    Objects.requireNonNull(result, "result must not be null");
    this.transactionResult = result;
    return this;
  }

  public GetTransactionListQuery type(final @NonNull BalanceModifier balanceModifier) {
    Objects.requireNonNull(balanceModifier, "balanceModifier must not be null");
    this.balanceModifier = balanceModifier;
    return this;
  }

  public GetTransactionListQuery accountId(
      final @NonNull Operator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return accountId(operator, AccountId.fromString(accountId));
  }

  public GetTransactionListQuery accountId(
      final @NonNull Operator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public GetTransactionListQuery timestamp(
      final @NonNull Operator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  public GetTransactionListQuery(MirrorNodeClient client) {
    super(client);
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/transactions")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (transactionType != null) {
      request.queryParam("transactiontype", transactionType.getValue());
    }

    if (transactionResult != null) {
      request.queryParam("result", transactionResult.getValue());
    }

    if (balanceModifier != null) {
      request.queryParam("type", balanceModifier.getValue());
    }

    if (accountId != null) {
      request.queryParam(
          "account.id", accountId.getOperator().getValue() + ":" + accountId.getValue().toString());
    }

    for (CriteriaParam<Instant> timestamp : timestamps) {
      request.queryParam(
          "timestamp",
          timestamp.getOperator().getValue()
              + ":"
              + timestamp.getValue().getEpochSecond()
              + "."
              + timestamp.getValue().getNano());
    }

    return request.build();
  }

  @Override
  List<Transaction> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTransactions(node);
  }
}
