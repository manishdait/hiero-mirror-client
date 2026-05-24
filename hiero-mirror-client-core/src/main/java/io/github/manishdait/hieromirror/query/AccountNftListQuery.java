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

/** Query to get nfts for an account. */
public class AccountNftListQuery extends AccountIdentifierQuery<AccountNftListQuery, Page<Nft>> {
  private Order order = Order.DESC;
  private int limit = 25;

  @Nullable private CriteriaParam<AccountId> spenderId;

  @Nullable private CriteriaParam<TokenId> tokenId;

  @Nullable private CriteriaParam<Long> serialNumber;

  /** Constructor. */
  public AccountNftListQuery() {}

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
  public AccountNftListQuery setOrder(final @NonNull Order order) {
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
  public AccountNftListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  /**
   * Gets the spenderId criteria filter.
   *
   * @return the spenderId criteria filter
   */
  public @Nullable CriteriaParam<AccountId> getSpenderId() {
    return spenderId;
  }

  /**
   * Sets the spenderId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param spenderId the string representation of accountId
   * @return {@code this}
   */
  public AccountNftListQuery setSpenderId(
      final @NonNull QueryOperator operator, final @NonNull String spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(spenderId, "spenderId must not be nul;");

    return setSpenderId(operator, AccountId.fromString(spenderId));
  }

  /**
   * Sets the spenderId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param spenderId the target {@link AccountId} instance
   * @return {@code this}
   */
  public AccountNftListQuery setSpenderId(
      final @NonNull QueryOperator operator, final @NonNull AccountId spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(spenderId, "spenderId must not be nul;");

    this.spenderId = new CriteriaParam<>(operator, spenderId);
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
  public AccountNftListQuery setTokenId(
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
  public AccountNftListQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be nul;");

    this.tokenId = new CriteriaParam<>(operator, tokenId);
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
  public AccountNftListQuery setSerialNumber(
      final @NonNull QueryOperator operator, final long serialNumber) {
    Objects.requireNonNull(operator, "operator must not be null");

    this.serialNumber = new CriteriaParam<>(operator, serialNumber);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequestInternal(final @NonNull MirrorNodeClient client) {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/accounts/" + identifier + "/nfts")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (spenderId != null) {
      request.queryParam(
          "spender.id", spenderId.getOperator().getValue() + ":" + spenderId.getValue().toString());
    }

    if (tokenId != null) {
      request.queryParam(
          "token.id", tokenId.getOperator().getValue() + ":" + tokenId.getValue().toString());
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
