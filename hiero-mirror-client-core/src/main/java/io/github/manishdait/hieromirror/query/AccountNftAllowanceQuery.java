package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NftAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

public class AccountNftAllowanceQuery
    extends AccountIdentifierQuery<AccountNftAllowanceQuery, Page<NftAllowance>> {
  private Order order = Order.DESC;
  private int limit = 25;
  private boolean owner = true;

  @Nullable private CriteriaParam<TokenId> tokenId;

  public AccountNftAllowanceQuery() {}

  public Order getOrder() {
    return order;
  }

  public AccountNftAllowanceQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public AccountNftAllowanceQuery setLimit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public boolean getOwner() {
    return owner;
  }

  public AccountNftAllowanceQuery setOwner(final boolean value) {
    this.owner = value;
    return this;
  }

  public @Nullable CriteriaParam<TokenId> getTokenId() {
    return tokenId;
  }

  public AccountNftAllowanceQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be nul;");
    this.tokenId = new CriteriaParam<>(operator, tokenId);

    return this;
  }

  @Override
  MirrorNodeRequest buildRequestInternal(final @NonNull MirrorNodeClient client) {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/accounts/" + identifier + "/allowances/nfts")
            .method("GET")
            .queryParam("owner", String.valueOf(owner))
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (tokenId != null) {
      request.queryParam(
          "token.id", tokenId.getOperator().getValue() + ":" + tokenId.getValue().toString());
    }

    return request.build();
  }

  @Override
  Page<NftAllowance> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseNftAllowances(node);
  }
}
