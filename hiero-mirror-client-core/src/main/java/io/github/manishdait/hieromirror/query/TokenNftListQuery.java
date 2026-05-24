package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get a list of non-fungible tokens */
public final class TokenNftListQuery extends Query<Page<Nft>> {
  private TokenId tokenId;

  private Order order = Order.DESC;
  private int limit = 25;

  @Nullable private CriteriaParam<AccountId> accountId;

  @Nullable private CriteriaParam<Long> serialNumber;

  /** Constructor. */
  public TokenNftListQuery() {}

  /**
   * Gets tokenId.
   *
   * @return the tokenId
   */
  public TokenId getTokenId() {
    return tokenId;
  }

  /**
   * Sets tokenId.
   *
   * @param tokenId string representation of tokenId
   * @return {@code this}
   */
  public TokenNftListQuery setTokenId(final @NonNull String tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return setTokenId(TokenId.fromString(tokenId));
  }

  /**
   * Sets tokenId.
   *
   * @param tokenId the target {@link TokenId} instance
   * @return {@code this}
   */
  public TokenNftListQuery setTokenId(final @NonNull TokenId tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    this.tokenId = tokenId;
    return this;
  }

  /**
   * Gets the sorting order for the query items. Defaults to {@code desc}.
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
  public TokenNftListQuery setOrder(final @NonNull Order order) {
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
  public TokenNftListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
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
  public TokenNftListQuery setAccountId(
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
  public TokenNftListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  /**
   * Gets the serialNumber criteria filter.
   *
   * @return the serialNumber criteria
   */
  public @Nullable CriteriaParam<Long> getSerialNumber() {
    return serialNumber;
  }

  /**
   * Sets the serialNumber criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param serialNumber the nft serial number
   * @return {@code this}
   */
  public TokenNftListQuery setSerialNumber(
      final @NonNull QueryOperator operator, final long serialNumber) {
    Objects.requireNonNull(operator, "operator must not be null");

    this.serialNumber = new CriteriaParam<>(operator, serialNumber);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (tokenId == null) {
      throw new IllegalStateException("tokenId must be set before executing query");
    }

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/tokens/" + tokenId + "/nfts")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (accountId != null) {
      request.queryParam(
          "account.id", accountId.getOperator().getValue() + ":" + accountId.getValue().toString());
    }

    if (serialNumber != null) {
      request.queryParam(
          "serialnumber", serialNumber.getOperator().getValue() + ":" + serialNumber.getValue());
    }

    return request.build();
  }

  @Override
  Page<Nft> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseNfts(node);
  }
}
