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
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get a list of tokens on the network. */
public final class TokenListQuery extends Query<Page<Token>> {
  private Order order = Order.ASC;
  private int limit = 25;

  @Nullable private String name;

  @Nullable private PublicKey publicKey;

  private List<TokenType> tokenTypes = new ArrayList<>();

  @Nullable private CriteriaParam<AccountId> accountId;

  @Nullable private CriteriaParam<TokenId> tokenId;

  /** Constructor. */
  public TokenListQuery() {}

  /**
   * Gets the sorting order for the query items. Defaults to {@code asc}.
   *
   * @return the {@link Order}
   */
  public Order getOrder() {
    return order;
  }

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  public TokenListQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  /**
   * Gets the maximum number of transactions to be retrieved. Defaults to {@code 25}.
   *
   * @return maximum number of records
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the maximum number of transactions to return. Must be within range: 1 to 100 inclusive.
   *
   * @param limit maximum items to return
   * @return {@code this}
   * @throws IllegalArgumentException if limit is outside range [1, 100]
   */
  public TokenListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  /**
   * Gets the name filter for the query.
   *
   * @return the name of token
   */
  public @Nullable String getName() {
    return name;
  }

  /**
   * Sets the name filter for the query.
   *
   * @param name of the token
   * @return {@code this}
   */
  public TokenListQuery setName(final @NonNull String name) {
    Objects.requireNonNull(name, "name must not be null");
    this.name = name;
    return this;
  }

  /**
   * Gets account publicKey criteria filter.
   *
   * @return the target {@link PublicKey}, or {@code null}
   */
  public PublicKey getPublicKey() {
    return publicKey;
  }

  /**
   * Sets the account public key criteria filter.
   *
   * @param publicKey the hex or DER encoded public key string
   * @return {@code this}
   */
  public TokenListQuery setPublicKey(final @NonNull String publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    return setPublicKey(PublicKey.fromString(publicKey));
  }

  /**
   * Sets the account public key criteria filter.
   *
   * @param publicKey the target {@link PublicKey} instance
   * @return {@code this}
   */
  public TokenListQuery setPublicKey(final @NonNull PublicKey publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    this.publicKey = publicKey;
    return this;
  }

  /**
   * Gets the accountId criteria filter.
   *
   * @return the account ID {@link CriteriaParam}, or {@code null}
   */
  public @Nullable CriteriaParam<AccountId> getAccountId() {
    return accountId;
  }

  /**
   * Sets an accountId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param accountId string representation of accountId
   * @return {@code this}
   */
  public TokenListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return setAccountId(operator, AccountId.fromString(accountId));
  }

  /**
   * Sets an accountId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param accountId the target {@link AccountId} instance
   * @return {@code this}
   */
  public TokenListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  /**
   * Gets the tokenId criteria filter.
   *
   * @return the tokenId criteria
   */
  public @Nullable CriteriaParam<TokenId> getTokenId() {
    return tokenId;
  }

  /**
   * Sets the tokenId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param tokenId the string representation of tokenId
   * @return {@code this}
   */
  public TokenListQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull String tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return setTokenId(operator, TokenId.fromString(tokenId));
  }

  /**
   * Sets the tokenId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param tokenId the target {@link TokenId} instance
   * @return {@code this}
   */
  public TokenListQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.tokenId = new CriteriaParam<>(operator, tokenId);
    return this;
  }

  /**
   * Gets the tokenTypes of which the token to fetch.
   *
   * @return list of tokenTypes
   */
  public List<TokenType> getTokenTypes() {
    return tokenTypes;
  }

  /**
   * Sets the tokenTypes of which the token to fetch.
   *
   * @param tokenTypes list of tokenTypes
   * @return {@code this}
   */
  public TokenListQuery setTokenTypes(final @NonNull List<TokenType> tokenTypes) {
    Objects.requireNonNull(tokenTypes, "tokenTypes must not be null");
    this.tokenTypes = new ArrayList<>(tokenTypes);
    return this;
  }

  /**
   * Clear the tokenTypes of which the token to fetch.
   *
   * @return {@code this}
   */
  public TokenListQuery clearTokenTypes() {
    tokenTypes.clear();
    return this;
  }

  /**
   * Adds a single tokenTypes to add to list.
   *
   * @param tokenType the tokenType to add
   * @return {@code this}
   */
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
