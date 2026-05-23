package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenType;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class TokenListQuery extends Query<Page<Token>> {
  private Order order = Order.DESC;
  private int limit = 25;
  private String name;
  private PublicKey publicKey;
  private List<TokenType> tokenTypes = new ArrayList<>();

  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<TokenId> tokenId;

  public TokenListQuery() {}

  public Order getOrder() {
    return order;
  }

  public TokenListQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public TokenListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  public String getName() {
    return name;
  }

  public TokenListQuery setName(final @NonNull String name) {
    Objects.requireNonNull(name, "name must not be null");
    this.name = name;
    return this;
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public TokenListQuery setPublicKey(final @NonNull String publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    return setPublicKey(PublicKey.fromString(publicKey));
  }

  public TokenListQuery setPublicKey(final @NonNull PublicKey publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    this.publicKey = publicKey;
    return this;
  }

  public CriteriaParam<AccountId> getAccountId() {
    return accountId;
  }

  public TokenListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return setAccountId(operator, AccountId.fromString(accountId));
  }

  public TokenListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public CriteriaParam<TokenId> getTokenId() {
    return tokenId;
  }

  public TokenListQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull String tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return setTokenId(operator, TokenId.fromString(tokenId));
  }

  public TokenListQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.tokenId = new CriteriaParam<>(operator, tokenId);
    return this;
  }

  public List<TokenType> getTokenTypes() {
    return tokenTypes;
  }

  public TokenListQuery setTokenTypes(final @NonNull List<TokenType> tokenTypes) {
    Objects.requireNonNull(tokenTypes, "tokenTypes must not be null");
    this.tokenTypes = new ArrayList<>(tokenTypes);
    return this;
  }

  public TokenListQuery clearTokenTypes() {
    tokenTypes.clear();
    return this;
  }

  public TokenListQuery addTokenType(final @NonNull TokenType tokenType) {
    Objects.requireNonNull(tokenType, "tokenType must not be null");
    this.tokenTypes.add(tokenType);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/tokens")
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
  Page<Token> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseTokens(node);
  }
}
