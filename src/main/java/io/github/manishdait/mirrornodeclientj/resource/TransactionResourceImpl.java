package io.github.manishdait.mirrornodeclientj.resource;

import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TransactionResource;
import io.github.manishdait.mirrornodeclientj.query.GetTransactionByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.GetTransactionListQuery;
import org.jspecify.annotations.NonNull;

public class TransactionResourceImpl implements TransactionResource {
  private final MirrorNodeClient client;

  public TransactionResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull GetTransactionListQuery findAll() {
    return new GetTransactionListQuery(client);
  }

  @Override
  public @NonNull GetTransactionByIdQuery findById(String transactionId) {
    return new GetTransactionByIdQuery(client, transactionId);
  }
}
