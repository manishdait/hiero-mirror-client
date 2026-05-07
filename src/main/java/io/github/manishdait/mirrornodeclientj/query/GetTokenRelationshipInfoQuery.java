package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.JsonParserImpl;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.Operator;
import io.github.manishdait.mirrornodeclientj.Order;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.data.TokenRelationShipInfo;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class GetTokenRelationshipInfoQuery extends Query<List<TokenRelationShipInfo>> {
  private final AccountId accountId;

  private Order order = Order.DESC;
  private int limit = 25;
  private CriteriaParam<TokenId> tokenId;

  public GetTokenRelationshipInfoQuery(MirrorNodeClient client, AccountId accountId) {
    super(client);
    this.accountId = accountId;
  }

  public GetTokenRelationshipInfoQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public GetTokenRelationshipInfoQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public GetTokenRelationshipInfoQuery tokenId(
      final @NonNull Operator operator, final @NonNull String tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return tokenId(operator, TokenId.fromString(tokenId));
  }

  public GetTokenRelationshipInfoQuery tokenId(
      final @NonNull Operator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.tokenId = new CriteriaParam<>(operator, tokenId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/accounts/" + accountId + "/tokens")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (tokenId != null) {
      request.queryParam(
          "token.id", tokenId.getOperator().getValue() + ":" + tokenId.getValue().toString());
    }

    return request.build();
  }

  @Override
  List<TokenRelationShipInfo> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTokenRelationships(node);
  }
}
