package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.BalanceModifier;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.Transaction;
import io.github.manishdait.hieromirror.model.TransactionResult;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.TransactionListQuery;
import io.github.manishdait.hieromirror.resource.wrapper.TransactionListRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class TransactionListRequestImpl implements TransactionListRequest {
  private final MirrorNodeClient client;

  private Integer limit;
  private Order order;
  private CriteriaParam<AccountId> accountId;
  private List<CriteriaParam<Instant>> timestamp;
  private TransactionType type;
  private TransactionResult result;
  private BalanceModifier modifier;

  public TransactionListRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull TransactionListRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull TransactionListRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull TransactionListRequest accountId(CriteriaParam<AccountId> accountId) {
    this.accountId = accountId;
    return this;
  }

  @Override
  public @NonNull TransactionListRequest timestamp(List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull TransactionListRequest transactionType(TransactionType type) {
    this.type = type;
    return this;
  }

  @Override
  public @NonNull TransactionListRequest result(TransactionResult result) {
    this.result = result;
    return this;
  }

  @Override
  public @NonNull TransactionListRequest type(BalanceModifier modifier) {
    this.modifier = modifier;
    return this;
  }

  @Override
  public @NonNull TransactionListQuery buildQuery() {
    TransactionListQuery query = new TransactionListQuery();

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (accountId != null) {
      query.setAccountId(accountId.getOperator(), accountId.getValue());
    }

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    if (type != null) {
      query.setTransactionType(type);
    }

    if (result != null) {
      query.setTransactionResult(result);
    }

    if (modifier != null) {
      query.setBalanceModifier(modifier);
    }

    return query;
  }

  @Override
  public @NonNull Page<Transaction> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<Transaction> call(@NonNull Duration timeout) {
    TransactionListQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
