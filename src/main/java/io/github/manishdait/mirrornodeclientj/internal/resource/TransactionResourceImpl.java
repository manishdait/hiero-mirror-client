package io.github.manishdait.mirrornodeclientj.internal.resource;

import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TransactionResource;
import io.github.manishdait.mirrornodeclientj.query.TransactionByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.TransactionListQuery;
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
  public @NonNull TransactionByIdQuery findById(String transactionId) {
    return new TransactionByIdQuery(client, transactionId);
  }
}
