package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.ScheduleInfo;
import io.github.manishdait.hieromirror.query.ScheduleListQuery;
import io.github.manishdait.hieromirror.resource.wrapper.ScheduleListRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class ScheduleListRequestImpl implements ScheduleListRequest {
  private final MirrorNodeClient client;

  private Order order;
  private Integer limit;
  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<ScheduleId> scheduleId;

  public ScheduleListRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull ScheduleListRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull ScheduleListRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull ScheduleListRequest accountId(CriteriaParam<AccountId> accountId) {
    this.accountId = accountId;
    return this;
  }

  @Override
  public @NonNull ScheduleListRequest scheduleId(CriteriaParam<ScheduleId> scheduleId) {
    this.scheduleId = scheduleId;
    return this;
  }

  @Override
  public @NonNull ScheduleListQuery buildQuery() {
    ScheduleListQuery query = new ScheduleListQuery();

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (accountId != null) {
      query.setAccountId(accountId.getOperator(), accountId.getValue());
    }

    if (scheduleId != null) {
      query.setScheduleId(scheduleId.getOperator(), scheduleId.getValue());
    }

    return query;
  }

  @Override
  public @NonNull Page<ScheduleInfo> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<ScheduleInfo> call(@NonNull Duration timeout) {
    ScheduleListQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
