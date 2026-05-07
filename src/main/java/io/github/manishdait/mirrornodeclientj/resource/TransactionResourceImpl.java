package io.github.manishdait.mirrornodeclientj.resource;

import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TransactionResource;
import io.github.manishdait.mirrornodeclientj.data.Transaction;
import io.github.manishdait.mirrornodeclientj.query.TransactionListQuery;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class TransactionResourceImpl implements TransactionResource {
  private final MirrorNodeClient client;

  public TransactionResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull TransactionListQuery findAll() {
    return new TransactionListQuery(client);
  }

  @Override
  public @NonNull Optional<Transaction> findById(String transactionId) {
    return Optional.empty();
  }
}
