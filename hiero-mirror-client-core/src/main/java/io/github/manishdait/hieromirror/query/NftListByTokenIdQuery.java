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

public class NftListByTokenIdQuery extends Query<Page<Nft>> {
  private final TokenId tokenId;

  private Order order = Order.ASC;
  private int limit = 25;

  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<Long> serialNumber;

  public NftListByTokenIdQuery(MirrorNodeClient client, TokenId tokenId) {
    super(client);
    this.tokenId = tokenId;
  }

  public TokenId getTokenId() {
    return tokenId;
  }

  public Order getOrder() {
    return order;
  }

  public int getLimit() {
    return limit;
  }

  public CriteriaParam<AccountId> getAccountId() {
    return accountId;
  }

  public CriteriaParam<Long> getSerialNumber() {
    return serialNumber;
  }

  public NftListByTokenIdQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public NftListByTokenIdQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public NftListByTokenIdQuery accountId(
      final @NonNull Operator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return accountId(operator, AccountId.fromString(accountId));
  }

  public NftListByTokenIdQuery accountId(
      final @NonNull Operator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public NftListByTokenIdQuery serial(final @NonNull Operator operator, final long serialNumber) {
    Objects.requireNonNull(operator, "operator must not be null");

    this.serialNumber = new CriteriaParam<>(operator, serialNumber);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/tokens/" + tokenId + "/nfts")
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
    return JsonParserImpl.parseNfts(node);
  }
}
