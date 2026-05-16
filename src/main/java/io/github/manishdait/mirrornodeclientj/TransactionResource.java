package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.query.TransactionByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.TransactionListQuery;
import org.jspecify.annotations.NonNull;

public interface TransactionResource {
  @NonNull TransactionListQuery findAll();

  @NonNull TransactionByIdQuery findById(String transactionId);
}
