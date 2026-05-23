package io.github.manishdait.mirrornodeclientj.core;

import io.github.manishdait.hieromirror.query.TransactionByIdQuery;
import io.github.manishdait.hieromirror.query.TransactionListQuery;
import org.jspecify.annotations.NonNull;

public interface TransactionResource {
  @NonNull TransactionListQuery findAll();

  @NonNull TransactionByIdQuery findById(String transactionId);
}
