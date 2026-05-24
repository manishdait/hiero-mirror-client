package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.BalanceModifier;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.Transaction;
import io.github.manishdait.hieromirror.model.TransactionResult;
import io.github.manishdait.hieromirror.model.TransactionType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get lists of transactions on the network. */
public final class TransactionListQuery extends Query<Page<Transaction>> {
  private Order order = Order.DESC;
  private int limit = 25;

  @Nullable private TransactionType transactionType;
  @Nullable private TransactionResult transactionResult;
  @Nullable private BalanceModifier balanceModifier;
  @Nullable private CriteriaParam<AccountId> accountId;

  private List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  /** Constructor. */
  public TransactionListQuery() {}

  /**
   * Gets the sorting order for the query items. Defaults to {@code desc}.
   *
   * @return the {@link Order}
   */
  public Order getOrder() {
    return order;
  }

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  public TransactionListQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  /**
   * Gets the maximum number of transactions to be retrieved. Defaults to {@code 25}.
   *
   * @return maximum number of records
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the maximum number of transactions to return. Must be within range: 1 to 100 inclusive.
   *
   * @param limit maximum items to return
   * @return {@code this}
   * @throws IllegalArgumentException if limit is outside range [1, 100]
   */
  public TransactionListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  /**
   * Gets the transactionType filter for transactions.
   *
   * @return {@link TransactionType}, or {@code null}
   */
  public @Nullable TransactionType getTransactionType() {
    return transactionType;
  }

  /**
   * Sets the transactionType filter for transactions.
   *
   * @param type {@link TransactionType} to be fetched
   * @return {@code this}
   */
  public TransactionListQuery setTransactionType(final @NonNull TransactionType type) {
    Objects.requireNonNull(type, "type must not be null");
    this.transactionType = type;
    return this;
  }

  /**
   * Gets the transactionResult filter for transactions.
   *
   * @return {@link TransactionResult}, or {@code null}
   */
  public @Nullable TransactionResult getTransactionResult() {
    return transactionResult;
  }

  /**
   * Sets the transactionResult filter for transactions.
   *
   * @param result {@link TransactionResult} to be fetched
   * @return {@code this}
   */
  public TransactionListQuery setTransactionResult(final @NonNull TransactionResult result) {
    Objects.requireNonNull(result, "result must not be null");
    this.transactionResult = result;
    return this;
  }

  /**
   * Gets the balanceModifier filter for transactions.
   *
   * @return {@link BalanceModifier}, or {@code null}
   */
  public @Nullable BalanceModifier getBalanceModifier() {
    return balanceModifier;
  }

  /**
   * Sets the balanceModifier filter for transactions.
   *
   * @param balanceModifier {@link BalanceModifier} to be fetched
   * @return {@code this}
   */
  public TransactionListQuery setBalanceModifier(final @NonNull BalanceModifier balanceModifier) {
    Objects.requireNonNull(balanceModifier, "balanceModifier must not be null");
    this.balanceModifier = balanceModifier;
    return this;
  }

  /**
   * Gets the accountId criteria filter.
   *
   * @return the account ID {@link CriteriaParam}, or {@code null}
   */
  public @Nullable CriteriaParam<AccountId> getAccountId() {
    return accountId;
  }

  /**
   * Sets an accountId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param accountId string representation of accountId
   * @return {@code this}
   */
  public TransactionListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return setAccountId(operator, AccountId.fromString(accountId));
  }

  /**
   * Sets an accountId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param accountId the target {@link AccountId} instance
   * @return {@code this}
   */
  public TransactionListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  /**
   * Gets the timestamp criteria filter.
   *
   * @return list of timestamp criteria.
   */
  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  /**
   * Sets the timestamp criteria filter using a list of timestamps.
   *
   * @param timestamps list of timestamp criterial params
   * @return {@code this}
   */
  public TransactionListQuery setTimestamps(
      final @NonNull List<CriteriaParam<Instant>> timestamps) {
    Objects.requireNonNull(timestamps, "timestamps must not be null");
    this.timestamps = new ArrayList<>(timestamps);
    return this;
  }

  /**
   * Clears all timestamp criteria filter.
   *
   * @return {@code this}
   */
  public TransactionListQuery clearTimestamps() {
    this.timestamps = new ArrayList<>();
    return this;
  }

  /**
   * Adds a single timestamp criteria to the filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param timestamp the timestamp to add
   * @return {@code this}
   */
  public TransactionListQuery addTimestamp(
      final @NonNull QueryOperator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/transactions")
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
    return MirrorNodeJsonParser.parseTransactions(node);
  }
}
