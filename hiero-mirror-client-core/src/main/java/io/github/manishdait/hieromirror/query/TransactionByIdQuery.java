package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Transaction;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class TransactionByIdQuery extends Query<Optional<Transaction>> {
  private final String transactionId;

  private Integer nonce;
  private Boolean scheduled;

  public TransactionByIdQuery(MirrorNodeClient client, String transactionId) {
    super(client);
    this.transactionId = transactionId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public Integer getNonce() {
    return nonce;
  }

  public Boolean getScheduled() {
    return scheduled;
  }

  public TransactionByIdQuery nonce(int nonce) {
    this.nonce = nonce;
    return this;
  }

  public TransactionByIdQuery scheduled(boolean scheduled) {
    this.scheduled = scheduled;
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/transactions/" + transactionId)
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
    List<Transaction> transactions = JsonParserImpl.parseTransactions(node).data();
    return transactions.isEmpty() ? Optional.empty() : Optional.of(transactions.getFirst());
  }
}
