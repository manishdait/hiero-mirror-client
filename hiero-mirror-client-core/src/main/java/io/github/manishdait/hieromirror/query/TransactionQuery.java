package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.Transaction;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get transaction by transactionId. */
public class TransactionQuery extends Query<Optional<Transaction>> {
  private String transactionId;

  @Nullable private Integer nonce;
  @Nullable private Boolean scheduled;

  /** Constructor. */
  public TransactionQuery() {}

  public String getTransactionId() {
    return transactionId;
  }

  public TransactionQuery setTransactionId(final @NonNull String transactionId) {
    Objects.requireNonNull(transactionId, "transactionId must not be null");
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Gets the nonce filter.
   *
   * @return the none
   */
  public @Nullable Integer getNonce() {
    return nonce;
  }

  /**
   * Sets the nonce filter.
   *
   * @param nonce the nonce
   * @return {@code this}
   */
  public TransactionQuery setNonce(Integer nonce) {
    this.nonce = nonce;
    return this;
  }

  /**
   * Gets the scheduled filter.
   *
   * @return the scheduled value
   */
  public @Nullable Boolean getScheduled() {
    return scheduled;
  }

  /**
   * Sets the scheduled filter.
   *
   * @param value the schedule value
   * @return {@code this}
   */
  public TransactionQuery setScheduled(Boolean value) {
    this.scheduled = value;
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (transactionId == null) {
      throw new IllegalStateException("transactionId must be set before executing transaction");
    }

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/transactions/" + transactionId)
            .method("GET");

    if (nonce != null) {
      request.queryParam("nonce", nonce.toString());
    }

    if (scheduled != null) {
      request.queryParam("scheduled", scheduled.toString());
    }

    return request.build();
  }

  @Override
  Optional<Transaction> mapResponse(@NonNull JsonNode node) {
    List<Transaction> transactions = MirrorNodeJsonParser.parseTransactions(node).data();
    return transactions.isEmpty() ? Optional.empty() : Optional.of(transactions.getFirst());
  }
}
