package io.github.manishdait.mirrornodeclientj.core.internal.resource;

import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.TransactionResource;
import io.github.manishdait.mirrornodeclientj.core.query.TransactionByIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.TransactionListQuery;
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
