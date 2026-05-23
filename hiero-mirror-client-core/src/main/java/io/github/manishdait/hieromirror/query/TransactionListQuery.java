package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.BalanceModifier;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Operator;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.Transaction;
import io.github.manishdait.hieromirror.model.TransactionResult;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class TransactionListQuery extends Query<Page<Transaction>> {
  private Order order = Order.DESC;
  private int limit = 25;
  private TransactionType transactionType;
  private TransactionResult transactionResult;
  private BalanceModifier balanceModifier;

  private CriteriaParam<AccountId> accountId;
  private final List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public TransactionListQuery(MirrorNodeClient client) {
    super(client);
  }

  public Order getOrder() {
    return order;
  }

  public int getLimit() {
    return limit;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public TransactionResult getTransactionResult() {
    return transactionResult;
  }

  public BalanceModifier getBalanceModifier() {
    return balanceModifier;
  }

  public CriteriaParam<AccountId> getAccountId() {
    return accountId;
  }

  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  public TransactionListQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public TransactionListQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public TransactionListQuery transactionType(final @NonNull TransactionType type) {
    Objects.requireNonNull(type, "type must not be null");
    this.transactionType = type;
    return this;
  }

  public TransactionListQuery result(final @NonNull TransactionResult result) {
    Objects.requireNonNull(result, "result must not be null");
    this.transactionResult = result;
    return this;
  }

  public TransactionListQuery type(final @NonNull BalanceModifier balanceModifier) {
    Objects.requireNonNull(balanceModifier, "balanceModifier must not be null");
    this.balanceModifier = balanceModifier;
    return this;
  }

  public TransactionListQuery accountId(
      final @NonNull Operator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return accountId(operator, AccountId.fromString(accountId));
  }

  public TransactionListQuery accountId(
      final @NonNull Operator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public TransactionListQuery timestamp(
      final @NonNull Operator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
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
  Page<Transaction> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseTransactions(node);
  }
}
