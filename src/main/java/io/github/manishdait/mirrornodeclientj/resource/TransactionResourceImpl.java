package io.github.manishdait.mirrornodeclientj.resource;

import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TransactionResource;
import io.github.manishdait.mirrornodeclientj.query.TransactionListQuery;
import io.github.manishdait.mirrornodeclientj.query.TransactionQuery;
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
  public @NonNull TransactionQuery findById(String transactionId) {
    return new TransactionQuery(client, transactionId);
  }
}
