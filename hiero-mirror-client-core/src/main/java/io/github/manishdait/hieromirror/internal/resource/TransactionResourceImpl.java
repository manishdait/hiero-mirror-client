package io.github.manishdait.hieromirror.internal.resource;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.TransactionListRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.TransactionRequestImpl;
import io.github.manishdait.hieromirror.resource.TransactionResource;
import io.github.manishdait.hieromirror.resource.wrapper.TransactionListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TransactionRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class TransactionResourceImpl implements TransactionResource {
  private final MirrorNodeClient client;

  public TransactionResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must nor be null");
    this.client = client;
  }

  @Override
  public @NonNull TransactionListRequest findAll() {
    return new TransactionListRequestImpl(client);
  }

  @Override
  public @NonNull TransactionRequest findById(@NonNull String transactionId) {
    Objects.requireNonNull(transactionId, "transactionId must not be null");
    return new TransactionRequestImpl(client, transactionId);
  }
}
