package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to List account entities on network. */
public final class AccountListQuery extends Query<Page<AccountInfo>> {
  private Order order = Order.ASC;
  private int limit = 25;
  private boolean includeBalance = true;
  @Nullable private PublicKey publicKey;

  @Nullable private CriteriaParam<AccountId> accountId;
  @Nullable private CriteriaParam<Hbar> balance;

  /** Constructor. */
  public AccountListQuery() {}

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
  public AccountListQuery setOrder(final @NonNull Order order) {
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
  public AccountListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }

    this.limit = limit;
    return this;
  }

  /**
   * Checks if balance fields are included in the response. Defaults to {@code true}.
   *
   * @return {@code true} if balances are fetched; otherwise {@code false}
   */
  public boolean getIncludeBalance() {
    return includeBalance;
  }

  /**
   * Set whether to include balance fields. Note: Included token balances are capped at 50 records
   * per account as outlined in HIP-367.
   *
   * @param value {@code true} to include balances, {@code false} to omit
   * @return {@code this}
   */
  public AccountListQuery setIncludeBalance(final boolean value) {
    this.includeBalance = value;
    return this;
  }

  /**
   * Gets account publicKey criteria filter.
   *
   * @return the target {@link PublicKey}, or {@code null}
   */
  public @Nullable PublicKey getPublicKey() {
    return publicKey;
  }

  /**
   * Sets the account public key criteria filter.
   *
   * @param publicKey the hex or DER encoded public key string
   * @return {@code this}
   */
  public AccountListQuery setPublicKey(final @NonNull String publicKey) {
    Objects.requireNonNull(publicKey, "publicKey must not be null");
    return setPublicKey(PublicKey.fromString(publicKey));
  }

  /**
   * Sets the account public key criteria filter.
   *
   * @param publicKey the target {@link PublicKey} instance
   * @return {@code this}
   */
  public AccountListQuery setPublicKey(final @NonNull PublicKey publicKey) {
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
  public AccountListQuery setAccountId(
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
  public AccountListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  /**
   * Gets the account balance criteria filter.
   *
   * @return the balance {@link CriteriaParam}, or {@code null}
   */
  public @Nullable CriteriaParam<Hbar> getBalance() {
    return balance;
  }

  /**
   * Sets an account balance criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param balance value expressed as long count of tinybars
   * @return {@code this}
   * @throws IllegalArgumentException if balance value drops below 0 tinybars
   */
  public AccountListQuery setBalance(final @NonNull QueryOperator operator, final long balance) {
    Objects.requireNonNull(operator, "operator must not be null");
    if (balance < 0) {
      throw new IllegalArgumentException("limit must be greater or equal to 0");
    }

    return setBalance(operator, Hbar.fromTinybars(balance));
  }

  /**
   * Sets an account balance criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param balance the instance of {@link Hbar}
   * @return {@code this}
   */
  public AccountListQuery setBalance(
      final @NonNull QueryOperator operator, final @NonNull Hbar balance) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(balance, "balance must not be null");

    this.balance = new CriteriaParam<>(operator, balance);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/accounts")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("balance", String.valueOf(includeBalance))
            .queryParam("order", order.getValue());

    if (publicKey != null) {
      request.queryParam("account.publickey", publicKey.toStringDER());
    }

    if (accountId != null) {
      request.queryParam(
          "account.id", accountId.getOperator().getValue() + ":" + accountId.getValue().toString());
    }

    if (balance != null) {
      request.queryParam(
          "account.balance",
          balance.getOperator().getValue() + ":" + String.valueOf(balance.getValue().toTinybars()));
    }

    return request.build();
  }

  @Override
  Page<AccountInfo> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseAccountInfos(node);
  }
}
