package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.query.GetTransactionByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.GetTransactionListQuery;
import org.jspecify.annotations.NonNull;

public interface TransactionResource {
  @NonNull GetTransactionListQuery findAll();

  @NonNull GetTransactionByIdQuery findById(String transactionId);
}
