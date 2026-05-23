package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.AccountQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountQueryWrapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class AccountQueryWrapperImpl implements AccountQueryWrapper {
  private final MirrorNodeClient client;
  private final String idOrAliasOrEvmAddress;

  private Order order;
  private Integer limit;
  private Boolean includeTransaction;
  private TransactionType transactionType;
  private List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public AccountQueryWrapperImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");
    this.client = client;
    this.idOrAliasOrEvmAddress = idOrAliasOrEvmAddress;
  }

  @Override
  public AccountQueryWrapper order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public AccountQueryWrapper limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public AccountQueryWrapper includeTransaction(Boolean includeTransaction) {
    this.includeTransaction = includeTransaction;
    return this;
  }

  @Override
  public AccountQueryWrapper transactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  @Override
  public AccountQueryWrapper timestamps(List<CriteriaParam<Instant>> timestamps) {
    this.timestamps = timestamps;
    return this;
  }

  @Override
  public Optional<AccountInfo> call() {
    AccountQuery query = new AccountQuery().setAlias(idOrAliasOrEvmAddress);

    if (order != null) {
      query.setOrder(order);
    }

    if (limit != null) {
      query.setLimit(limit);
    }

    if (includeTransaction != null) {
      query.setIncludeTransaction(includeTransaction);
    }

    if (transactionType != null) {
      query.setTransactionType(transactionType);
    }

    if (timestamps != null) {
      query.setTimestamp(timestamps);
    }

    return query.execute(client);
  }
}
