package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.data.Transaction;
import io.github.manishdait.mirrornodeclientj.query.TransactionListQuery;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public interface TransactionResource {
  @NonNull TransactionListQuery findAll();

  @NonNull Optional<Transaction> findById(String transactionId);
}
