package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.AccountInfo;
import io.github.manishdait.mirrornodeclientj.core.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import io.github.manishdait.mirrornodeclientj.core.data.Order;
import io.github.manishdait.mirrornodeclientj.core.data.TransactionType;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class AccountByIdQuery extends Query<Optional<AccountInfo>> {
  private final AccountId accountId;

  private Order order = Order.DESC;
  private int limit = 25;
  private boolean includeTransaction = false;
  private TransactionType transactionType;
  private final List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public AccountByIdQuery(MirrorNodeClient client, AccountId accountId) {
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

  public boolean isIncludeTransaction() {
    return includeTransaction;
  }

  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public AccountByIdQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public AccountByIdQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public AccountByIdQuery includeTransaction(final boolean includeTransaction) {
    this.includeTransaction = includeTransaction;
    return this;
  }

  public AccountByIdQuery transactionType(final @NonNull TransactionType transactionType) {
    Objects.requireNonNull(transactionType, "transactionType must not be null");
    this.transactionType = transactionType;
    return this;
  }

  public AccountByIdQuery timestamp(
      final @NonNull Operator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  public @NonNull CryptoAllowanceQuery cryptoAllowance() {
    return new CryptoAllowanceQuery(client, accountId);
  }

  public @NonNull TokenAllowanceQuery tokenAllowance() {
    return new TokenAllowanceQuery(client, accountId);
  }

  public @NonNull NftAllowanceQuery nftAllowance() {
    return new NftAllowanceQuery(client, accountId);
  }

  public @NonNull StackingRewardQuery pastStakingRewards() {
    return new StackingRewardQuery(client, accountId);
  }
  ;

  public @NonNull TokenRelationshipInfoQuery tokenRelationshipInfo() {
    return new TokenRelationshipInfoQuery(client, accountId);
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
