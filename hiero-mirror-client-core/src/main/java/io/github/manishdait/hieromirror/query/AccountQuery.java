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
public class AccountQuery extends AccountIdentifierQuery<AccountQuery, Optional<AccountInfo>> {
  private Order order = Order.DESC;
  private int limit = 25;
  private boolean includeTransaction = false;
  @Nullable private TransactionType transactionType;

  private List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public AccountQuery() {}

  public Order getOrder() {
    return order;
  }

  public AccountQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public AccountQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }

    this.limit = limit;
    return this;
  }

  public boolean getIncludeTransaction() {
    return includeTransaction;
  }

  public AccountQuery setIncludeTransaction(final boolean value) {
    this.includeTransaction = value;
    return this;
  }

  public @Nullable TransactionType getTransactionType() {
    return transactionType;
  }

  public AccountQuery setTransactionType(final @NonNull TransactionType transactionType) {
    Objects.requireNonNull(transactionType, "transactionType must not be null");
    this.transactionType = transactionType;
    return this;
  }

  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  public AccountQuery setTimestamp(final @NonNull List<CriteriaParam<Instant>> timestamps) {
    Objects.requireNonNull(timestamps, "timestamps must not be null");
    this.timestamps = new ArrayList<>(timestamps);
    return this;
  }

  public AccountQuery clearTimestamps() {
    this.timestamps = new ArrayList<>();
    return this;
  }

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
