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
import tools.jackson.databind.JsonNode;

public class TokenNftListQuery extends Query<Page<Nft>> {
  private TokenId tokenId;

  private Order order = Order.ASC;
  private int limit = 25;

  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<Long> serialNumber;

  public TokenNftListQuery() {}

  public TokenId getTokenId() {
    return tokenId;
  }

  public TokenNftListQuery setTokenId(final @NonNull String tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return setTokenId(TokenId.fromString(tokenId));
  }

  public TokenNftListQuery setTokenId(final @NonNull TokenId tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    this.tokenId = tokenId;
    return this;
  }

  public Order getOrder() {
    return order;
  }

  public TokenNftListQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public TokenNftListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  public CriteriaParam<AccountId> getAccountId() {
    return accountId;
  }

  public TokenNftListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return setAccountId(operator, AccountId.fromString(accountId));
  }

  public TokenNftListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public CriteriaParam<Long> getSerialNumber() {
    return serialNumber;
  }

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
