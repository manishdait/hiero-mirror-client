package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import io.github.manishdait.mirrornodeclientj.core.data.Order;
import io.github.manishdait.mirrornodeclientj.core.data.Page;
import io.github.manishdait.mirrornodeclientj.core.data.ScheduleInfo;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class ScheduleListQuery extends Query<Page<ScheduleInfo>> {
  private Order order = Order.ASC;
  private int limit = 25;

  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<ScheduleId> scheduleId;

  public ScheduleListQuery(MirrorNodeClient client) {
    super(client);
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

  public CriteriaParam<ScheduleId> getScheduleId() {
    return scheduleId;
  }

  public ScheduleListQuery order(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public ScheduleListQuery limit(final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit must be greater than 0");
    }
    this.limit = limit;
    return this;
  }

  public ScheduleListQuery accountId(
      final @NonNull Operator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return accountId(operator, AccountId.fromString(accountId));
  }

  public ScheduleListQuery accountId(
      final @NonNull Operator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public ScheduleListQuery scheduleId(
      final @NonNull Operator operator, final @NonNull String scheduleId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");
    return scheduleId(operator, ScheduleId.fromString(scheduleId));
  }

  public ScheduleListQuery scheduleId(
      final @NonNull Operator operator, final @NonNull ScheduleId scheduleId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");

    this.scheduleId = new CriteriaParam<>(operator, scheduleId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/schedules")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    if (scheduleId != null) {
      request.queryParam(
          "schedule.id",
          scheduleId.getOperator().getValue() + ":" + scheduleId.getValue().toString());
    }

    if (accountId != null) {
      request.queryParam(
          "account.id", accountId.getOperator().getValue() + ":" + accountId.getValue().toString());
    }

    return request.build();
  }

  @Override
  Page<ScheduleInfo> mapResponse(JsonNode node) {
    return JsonParserImpl.parseScheduleInfos(node);
  }
}
