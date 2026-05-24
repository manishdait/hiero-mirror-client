package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.CryptoAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get crypto allowances for an account. */
public class AccountCryptoAllowanceQuery
    extends AccountIdentifierQuery<AccountCryptoAllowanceQuery, Page<CryptoAllowance>> {
  private Order order = Order.DESC;
  private int limit = 25;

  @Nullable private CriteriaParam<AccountId> spenderId;

  /** Constructor. */
  public AccountCryptoAllowanceQuery() {}

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
  public AccountCryptoAllowanceQuery setOrder(final @NonNull Order order) {
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
  public AccountCryptoAllowanceQuery setLimit(final int limit) {
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
  public AccountCryptoAllowanceQuery setSpenderId(
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
  public AccountCryptoAllowanceQuery setSpenderId(
      final @NonNull QueryOperator operator, final @NonNull AccountId spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(spenderId, "spenderId must not be nul;");

    this.spenderId = new CriteriaParam<>(operator, spenderId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequestInternal(final @NonNull MirrorNodeClient client) {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/accounts/" + identifier + "/allowances/crypto")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (spenderId != null) {
      request.queryParam(
          "spender.id", spenderId.getOperator().getValue() + ":" + spenderId.getValue().toString());
    }

    return request.build();
  }

  @Override
  Page<CryptoAllowance> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseCryptoAllowances(node);
  }
}
