package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.TransactionType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get an account by its alias, id, or evm address. */
public final class AccountQuery
    extends AccountIdentifierQuery<AccountQuery, Optional<AccountInfo>> {
  private Order order = Order.DESC;
  private int limit = 25;
  private boolean includeTransaction = true;

  @Nullable private TransactionType transactionType;

  private List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  /** Constructor. */
  public AccountQuery() {}

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
  public AccountQuery setOrder(final @NonNull Order order) {
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
  public AccountQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }

    this.limit = limit;
    return this;
  }

  /**
   * Checks if transaction field are included in the response. Defaults to {@code true}.
   *
   * @return {@code true} if transactions are fetched; otherwise {@code false}
   */
  public boolean getIncludeTransaction() {
    return includeTransaction;
  }

  /**
   * Set whether to include transactions fields.
   *
   * @param value {@code true} to include transactions, {@code false} to omit
   * @return {@code this}
   */
  public AccountQuery setIncludeTransaction(final boolean value) {
    this.includeTransaction = value;
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
   * @param transactionType {@link TransactionType} to be fetched
   * @return {@code this}
   */
  public AccountQuery setTransactionType(final @NonNull TransactionType transactionType) {
    Objects.requireNonNull(transactionType, "transactionType must not be null");
    this.transactionType = transactionType;
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
  public AccountQuery setTimestamps(final @NonNull List<CriteriaParam<Instant>> timestamps) {
    Objects.requireNonNull(timestamps, "timestamps must not be null");
    this.timestamps = new ArrayList<>(timestamps);
    return this;
  }

  /**
   * Clears all timestamp criteria filter.
   *
   * @return {@code this}
   */
  public AccountQuery clearTimestamps() {
    this.timestamps.clear();
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
  public AccountQuery addTimestamp(
      final @NonNull QueryOperator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  @Override
  MirrorNodeRequest buildRequestInternal(final @NonNull MirrorNodeClient client) {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/accounts/" + identifier)
            .method("GET")
            .queryParam("transactions", String.valueOf(includeTransaction))
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    for (CriteriaParam<Instant> timestamp : timestamps) {
      request.queryParam(
          "timestamp",
          timestamp.getOperator().getValue()
              + ":"
              + timestamp.getValue().getEpochSecond()
              + "."
              + timestamp.getValue().getNano());
    }

    if (transactionType != null) {
      request.queryParam("transactiontype", transactionType.getValue());
    }

    return request.build();
  }

  @Override
  Optional<AccountInfo> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseAccountInfo(node);
  }
}
