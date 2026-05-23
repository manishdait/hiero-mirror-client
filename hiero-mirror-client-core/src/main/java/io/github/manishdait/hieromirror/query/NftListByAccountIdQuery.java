package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.Operator;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class NftListByAccountIdQuery extends Query<Page<Nft>> {
  private final AccountId accountId;

  private Order order = Order.ASC;
  private int limit = 25;

  private CriteriaParam<AccountId> spenderId;
  private CriteriaParam<TokenId> tokenId;
  private CriteriaParam<Long> serialNumber;

  public NftListByAccountIdQuery(MirrorNodeClient client, AccountId accountId) {
    super(client);
    this.accountId = accountId;
  }

  public AccountId getAccountId() {
    return accountId;
  }

  public Order getOrder() {
    return order;
  }

  public int getLimit() {
    return limit;
  }

  public CriteriaParam<AccountId> getSpenderId() {
    return spenderId;
  }

  public CriteriaParam<TokenId> getTokenId() {
    return tokenId;
  }

  public CriteriaParam<Long> getSerialNumber() {
    return serialNumber;
  }

  public NftListByAccountIdQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public NftListByAccountIdQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public NftListByAccountIdQuery spenderId(
      final @NonNull Operator operator, final @NonNull String spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(spenderId, "spenderId must not be null");
    return spenderId(operator, AccountId.fromString(spenderId));
  }

  public NftListByAccountIdQuery spenderId(
      final @NonNull Operator operator, final @NonNull AccountId spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(spenderId, "spenderId must not be null");

    this.spenderId = new CriteriaParam<>(operator, spenderId);
    return this;
  }

  public NftListByAccountIdQuery tokenId(
      final @NonNull Operator operator, final @NonNull String spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return tokenId(operator, TokenId.fromString(spenderId));
  }

  public NftListByAccountIdQuery tokenId(
      final @NonNull Operator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.tokenId = new CriteriaParam<>(operator, tokenId);
    return this;
  }

  public NftListByAccountIdQuery serial(final @NonNull Operator operator, final long serialNumber) {
    Objects.requireNonNull(operator, "operator must not be null");

    this.serialNumber = new CriteriaParam<>(operator, serialNumber);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/accounts/" + accountId + "/nfts")
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
    return JsonParserImpl.parseNfts(node);
  }
}
