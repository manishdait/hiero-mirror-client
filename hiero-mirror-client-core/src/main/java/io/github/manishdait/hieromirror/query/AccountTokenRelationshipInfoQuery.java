package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.TokenRelationShip;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

public class AccountTokenRelationshipInfoQuery
    extends AccountIdentifierQuery<AccountTokenRelationshipInfoQuery, Page<TokenRelationShip>> {
  private Order order = Order.DESC;
  private int limit = 25;
  private @Nullable CriteriaParam<TokenId> tokenId;

  public AccountTokenRelationshipInfoQuery() {}

  public Order getOrder() {
    return order;
  }

  public AccountTokenRelationshipInfoQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public AccountTokenRelationshipInfoQuery setLimit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public @Nullable CriteriaParam<TokenId> getTokenId() {
    return tokenId;
  }

  public AccountTokenRelationshipInfoQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull String tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return setTokenId(operator, TokenId.fromString(tokenId));
  }

  public AccountTokenRelationshipInfoQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.tokenId = new CriteriaParam<>(operator, tokenId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequestInternal(final @NonNull MirrorNodeClient client) {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/accounts/" + identifier + "/tokens")
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
  Page<TokenRelationShip> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseTokenRelationships(node);
  }
}
