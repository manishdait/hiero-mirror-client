package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.ScheduleInfo;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to list schedules entities. */
public final class ScheduleListQuery extends Query<Page<ScheduleInfo>> {
  private Order order = Order.ASC;
  private int limit = 25;

  @Nullable private CriteriaParam<AccountId> accountId;
  @Nullable private CriteriaParam<ScheduleId> scheduleId;

  /** Constructor. */
  public ScheduleListQuery() {}

  /**
   * Gets the sorting order for the query items. Defaults to {@code asc}.
   *
   * @return the {@link Order}
   */
  public Order getOrder() {
    return order;
  }

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  public ScheduleListQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  /**
   * Gets the maximum number of items to be retrieved. Defaults to {@code 25}.
   *
   * @return maximum number of records
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the maximum number of items to return. Must be within range: 1 to 100 inclusive.
   *
   * @param limit maximum items to return
   * @return {@code this}
   * @throws IllegalArgumentException if limit is outside range [1, 100]
   */
  public ScheduleListQuery setLimit(final int limit) {
    if (limit < 0 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  /**
   * Gets the accountId criteria filter.
   *
   * @return the account ID {@link CriteriaParam}, or {@code null}
   */
  public @Nullable CriteriaParam<AccountId> getAccountId() {
    return accountId;
  }

  /**
   * Sets an accountId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param accountId string representation of accountId
   * @return {@code this}
   */
  public ScheduleListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull String accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");
    return setAccountId(operator, AccountId.fromString(accountId));
  }

  /**
   * Sets an accountId criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param accountId the target {@link AccountId} instance
   * @return {@code this}
   */
  public ScheduleListQuery setAccountId(
      final @NonNull QueryOperator operator, final @NonNull AccountId accountId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(accountId, "accountId must not be null");

    this.accountId = new CriteriaParam<>(operator, accountId);
    return this;
  }

  public @Nullable CriteriaParam<ScheduleId> getScheduleId() {
    return scheduleId;
  }

  public ScheduleListQuery setScheduleId(
      final @NonNull QueryOperator operator, final @NonNull String scheduleId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");
    return setScheduleId(operator, ScheduleId.fromString(scheduleId));
  }

  public ScheduleListQuery setScheduleId(
      final @NonNull QueryOperator operator, final @NonNull ScheduleId scheduleId) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");

    this.scheduleId = new CriteriaParam<>(operator, scheduleId);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/schedules")
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
  Page<ScheduleInfo> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseScheduleInfos(node);
  }
}
