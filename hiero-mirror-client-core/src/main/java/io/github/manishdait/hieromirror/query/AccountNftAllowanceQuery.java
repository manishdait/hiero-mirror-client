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

/** Query to get non-fungible token allowances for an account. */
public class AccountNftAllowanceQuery
    extends AccountIdentifierQuery<AccountNftAllowanceQuery, Page<NftAllowance>> {
  private Order order = Order.ASC;
  private int limit = 25;
  private boolean owner = true;

  @Nullable private CriteriaParam<TokenId> tokenId;

  /** Constructor. */
  public AccountNftAllowanceQuery() {}

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
  public AccountNftAllowanceQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  /**
   * Gets the maximum number of items to be retrieved. Defaults to {@code 25}.
   *
   * @return maximum number of records
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the maximum number of items to return. Must be within range: 1 to 100 inclusive.
   *
   * @param limit maximum items to return
   * @return {@code this}
   * @throws IllegalArgumentException if limit is outside range [1, 100]
   */
  public AccountNftAllowanceQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  /**
   * Gets whether the accountId filters by allowance owner. Default {@code true}
   *
   * @return accountId filters by allowance owner.
   */
  public boolean getOwner() {
    return owner;
  }

  /**
   * Sets whether accountId specifies the allowance owner (true) or spender (false).
   *
   * @param value true to retrieve allowances granted by the owner
   * @return {@code this}
   */
  public AccountNftAllowanceQuery setOwner(final boolean value) {
    this.owner = value;
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
  public AccountNftAllowanceQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull String tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be nul;");

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
