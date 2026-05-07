package io.github.manishdait.mirrornodeclientj.query;

import io.github.manishdait.mirrornodeclientj.JsonParserImpl;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.data.Transaction;
import java.util.List;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class TransactionQuery extends Query<Optional<Transaction>> {
  private final String transactionId;

  Integer nonce;
  Boolean scheduled;

  public TransactionQuery(MirrorNodeClient client, String transactionId) {
    super(client);
    this.transactionId = transactionId;
  }

  public TransactionQuery nonce(int nonce) {
    this.nonce = nonce;
    return this;
  }

  public TransactionQuery scheduled(boolean scheduled) {
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
  Optional<Transaction> mapResponse(JsonNode node) {
    List<Transaction> transactions = JsonParserImpl.parseTransactions(node);
    return transactions.isEmpty() ? Optional.empty() : Optional.of(transactions.getFirst());
  }
}
