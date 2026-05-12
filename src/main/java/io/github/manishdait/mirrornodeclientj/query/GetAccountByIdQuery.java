package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.Operator;
import io.github.manishdait.mirrornodeclientj.Order;
import io.github.manishdait.mirrornodeclientj.TransactionType;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.data.AccountInfo;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class GetAccountByIdQuery extends Query<Optional<AccountInfo>> {
  private final AccountId accountId;

  private Order order = Order.DESC;
  private int limit = 25;
  private boolean includeTransaction = false;
  private TransactionType transactionType;
  private final List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public GetAccountByIdQuery(MirrorNodeClient client, AccountId accountId) {
    super(client);
    this.accountId = accountId;
  }

  public GetAccountByIdQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public GetAccountByIdQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public GetAccountByIdQuery includeTransaction(final boolean includeTransaction) {
    this.includeTransaction = includeTransaction;
    return this;
  }

  public GetAccountByIdQuery transactionType(final @NonNull TransactionType transactionType) {
    Objects.requireNonNull(transactionType, "transactionType must not be null");
    this.transactionType = transactionType;
    return this;
  }

  public GetAccountByIdQuery timestamp(
      final @NonNull Operator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  public @NonNull GetCryptoAllowanceQuery cryptoAllowance() {
    return new GetCryptoAllowanceQuery(client, accountId);
  }

  public @NonNull GetTokenAllowanceQuery tokenAllowance() {
    return new GetTokenAllowanceQuery(client, accountId);
  }

  public @NonNull GetNftAllowanceQuery nftAllowance() {
    return new GetNftAllowanceQuery(client, accountId);
  }

  public @NonNull GetStackingRewardQuery pastStakingRewards() {
    return new GetStackingRewardQuery(client, accountId);
  }
  ;

  public @NonNull GetTokenRelationshipInfoQuery tokenRelationshipInfo() {
    return new GetTokenRelationshipInfoQuery(client, accountId);
  }
  ;

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/accounts/" + accountId)
            .method("GET")
            .queryParam("transactions", String.valueOf(includeTransaction))
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    for (CriteriaParam<Instant> timestamp : timestamps) {
      request.queryParam(
          "timestamp",
          timestamp.getOperator().getValue()
              + ":"
              + timestamp.getValue().getEpochSecond()
              + "."
              + timestamp.getValue().getNano());
    }

    if (transactionType != null) {
      request.queryParam("transactiontype", transactionType.getValue());
    }

    return request.build();
  }

  @Override
  Optional<AccountInfo> mapResponse(JsonNode node) {
    return JsonParserImpl.parseAccountInfo(node);
  }
}
