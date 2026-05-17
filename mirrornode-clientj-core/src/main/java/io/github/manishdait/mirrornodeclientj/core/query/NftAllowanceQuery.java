package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.core.data.NftAllowance;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import io.github.manishdait.mirrornodeclientj.core.data.Order;
import io.github.manishdait.mirrornodeclientj.core.data.Page;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class NftAllowanceQuery extends Query<Page<NftAllowance>> {
  private final AccountId accountId;

  private Order order = Order.DESC;
  private int limit = 25;
  private boolean owner = true;
  private CriteriaParam<TokenId> tokenId;

  public NftAllowanceQuery(MirrorNodeClient client, AccountId accountId) {
    super(client);
    this.accountId = accountId;
  }

  public NftAllowanceQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public NftAllowanceQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public NftAllowanceQuery owner(final boolean owner) {
    this.owner = owner;
    return this;
  }

  public NftAllowanceQuery tokenId(
      final @NonNull Operator operator, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be nul;");
    this.tokenId = new CriteriaParam<>(operator, tokenId);

    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/accounts/" + accountId + "/allowances/nfts")
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
  Page<NftAllowance> mapResponse(JsonNode node) {
    return JsonParserImpl.parseNftAllowances(node);
  }
}
