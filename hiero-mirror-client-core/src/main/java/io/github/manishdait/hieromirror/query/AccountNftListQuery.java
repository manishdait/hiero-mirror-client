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

public class AccountNftListQuery extends AccountIdentifierQuery<AccountNftListQuery, Page<Nft>> {
  private Order order = Order.ASC;
  private int limit = 25;

  private CriteriaParam<AccountId> spenderId;
  private CriteriaParam<TokenId> tokenId;
  private CriteriaParam<Long> serialNumber;

  public AccountNftListQuery() {}

  public Order getOrder() {
    return order;
  }

  public AccountNftListQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public AccountNftListQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  public CriteriaParam<AccountId> getSpenderId() {
    return spenderId;
  }

  public AccountNftListQuery setSpenderId(
      final @NonNull QueryOperator operator, final @NonNull String spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(spenderId, "spenderId must not be null");
    return setSpenderId(operator, AccountId.fromString(spenderId));
  }

  public AccountNftListQuery setSpenderId(
      final @NonNull QueryOperator operator, final @NonNull AccountId spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(spenderId, "spenderId must not be null");

    this.spenderId = new CriteriaParam<>(operator, spenderId);
    return this;
  }

  public CriteriaParam<TokenId> getTokenId() {
    return tokenId;
  }

  public AccountNftListQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull String spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return setTokenId(operator, TokenId.fromString(spenderId));
  }

  public AccountNftListQuery setTokenId(
      final @NonNull QueryOperator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.tokenId = new CriteriaParam<>(operator, tokenId);
    return this;
  }

  public CriteriaParam<Long> getSerialNumber() {
    return serialNumber;
  }

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
