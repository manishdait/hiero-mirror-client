package io.github.manishdait.mirrornodeclientj.core.query;

import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.Transaction;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.List;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class TransactionByIdQuery extends Query<Optional<Transaction>> {
  private final String transactionId;

  Integer nonce;
  Boolean scheduled;

  public TransactionByIdQuery(MirrorNodeClient client, String transactionId) {
    super(client);
    this.transactionId = transactionId;
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
  Optional<Transaction> mapResponse(JsonNode node) {
    List<Transaction> transactions = JsonParserImpl.parseTransactions(node).data();
    return transactions.isEmpty() ? Optional.empty() : Optional.of(transactions.getFirst());
  }
}
