package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.query.TransactionListQuery;
import io.github.manishdait.mirrornodeclientj.query.TransactionQuery;
import org.jspecify.annotations.NonNull;

public interface TransactionResource {
  @NonNull TransactionListQuery findAll();

  @NonNull TransactionQuery findById(String transactionId);
}
