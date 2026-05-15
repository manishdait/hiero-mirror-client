package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenType;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.data.Operator;
import io.github.manishdait.mirrornodeclientj.data.Order;
import io.github.manishdait.mirrornodeclientj.data.Token;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class GetTokenListQuery extends Query<List<Token>> {
  private Order order = Order.DESC;
  private int limit = 25;
  private String name;
  private PublicKey publicKey;
  private final List<TokenType> tokenTypes = new ArrayList<>();

  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<TokenId> tokenId;

  public GetTokenListQuery(MirrorNodeClient client) {
    super(client);
  }

  public GetTokenListQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public GetTokenListQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public GetTokenListQuery name(final @NonNull String name) {
    Objects.requireNonNull(name, "name must not be null");
    this.name = name;
    return this;
  }

  public GetTokenListQuery publicKey(final @NonNull String publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    return publicKey(PublicKey.fromString(publicKey));
  }

  public GetTokenListQuery publicKey(final @NonNull PublicKey publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    this.publicKey = publicKey;
    return this;
  }

  public GetTokenListQuery accountId(
      final @NonNull Operator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return accountId(operator, AccountId.fromString(accountId));
  }

  public GetTokenListQuery accountId(
      final @NonNull Operator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public GetTokenListQuery tokenId(
      final @NonNull Operator operator, final @NonNull String tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return tokenId(operator, TokenId.fromString(tokenId));
  }

  public GetTokenListQuery tokenId(
      final @NonNull Operator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.tokenId = new CriteriaParam<>(operator, tokenId);
    return this;
  }

  public GetTokenListQuery type(final @NonNull TokenType tokenType) {
    Objects.requireNonNull(tokenType, "tokenType must not be null");
    this.tokenTypes.add(tokenType);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/tokens")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (publicKey != null) {
      request.queryParam("publickey", publicKey.toStringDER());
    }

    if (name != null) {
      request.queryParam("name", name);
    }

    if (accountId != null) {
      request.queryParam(
          "account.id", accountId.getOperator().getValue() + ":" + accountId.getValue().toString());
    }

    if (tokenId != null) {
      request.queryParam(
          "token.id", tokenId.getOperator().getValue() + ":" + tokenId.getValue().toString());
    }

    for (TokenType tokenType : tokenTypes) {
      request.queryParam("type", tokenType.toString());
    }

    return request.build();
  }

  @Override
  List<Token> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTokens(node);
  }
}
