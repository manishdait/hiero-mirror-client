package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.core.data.CryptoAllowance;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import io.github.manishdait.mirrornodeclientj.core.data.Order;
import io.github.manishdait.mirrornodeclientj.core.data.Page;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class CryptoAllowanceQuery extends Query<Page<CryptoAllowance>> {
  private final AccountId accountId;

  private Order order = Order.DESC;
  private int limit = 25;
  private CriteriaParam<AccountId> spenderId;

  public CryptoAllowanceQuery(MirrorNodeClient client, AccountId accountId) {
    super(client);
    this.accountId = accountId;
  }

  public CryptoAllowanceQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public CryptoAllowanceQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public CryptoAllowanceQuery spenderId(
      final @NonNull Operator operator, final @NonNull AccountId spenderId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(spenderId, "spenderId must not be nul;");
    this.spenderId = new CriteriaParam<>(operator, spenderId);

    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/accounts/" + accountId + "/allowances/crypto")
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
  Page<CryptoAllowance> mapResponse(JsonNode node) {
    return JsonParserImpl.parseCryptoAllowances(node);
  }
}
