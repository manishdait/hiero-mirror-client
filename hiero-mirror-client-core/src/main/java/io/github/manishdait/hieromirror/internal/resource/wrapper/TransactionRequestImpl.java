package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Transaction;
import io.github.manishdait.hieromirror.query.TransactionQuery;
import io.github.manishdait.hieromirror.resource.wrapper.TransactionRequest;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class TransactionRequestImpl implements TransactionRequest {
  private final MirrorNodeClient client;
  private final String transactionId;

  private Integer nonce;
  private Boolean schedule;

  public TransactionRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String transactionId) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(transactionId, "transactionId must not be null");

    this.client = client;
    this.transactionId = transactionId;
  }

  @Override
  public @NonNull TransactionRequest nonce(int nonce) {
    this.nonce = nonce;
    return this;
  }

  @Override
  public @NonNull TransactionRequest schedule(boolean value) {
    this.schedule = value;
    return this;
  }

  @Override
  public @NonNull TransactionQuery buildQuery() {
    TransactionQuery query = new TransactionQuery().setTransactionId(transactionId);

    if (nonce != null) {
      query.setNonce(nonce);
    }

    if (schedule != null) {
      query.setScheduled(schedule);
    }

    return query;
  }

  @Override
  public @NonNull Optional<Transaction> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<Transaction> call(@NonNull Duration timeout) {
    TransactionQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
