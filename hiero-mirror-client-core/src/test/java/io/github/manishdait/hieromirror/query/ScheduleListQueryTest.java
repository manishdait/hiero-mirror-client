package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ScheduleListQueryTest {
  private final Order ORDER = Order.DESC;
  private final int LIMIT = 10;
  private final CriteriaParam<ScheduleId> SCHEDULE_ID =
      new CriteriaParam<>(QueryOperator.EQ, new ScheduleId(0, 0, 1));
  private final CriteriaParam<AccountId> ACCOUNT_ID =
      new CriteriaParam<>(QueryOperator.EQ, new AccountId(0, 0, 1));

  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new ScheduleListQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.ASC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getScheduleId()).isNull();
    Assertions.assertThat(query.getAccountId()).isNull();
  }

  @Test
  void shouldSetOrder() {
    var query = new ScheduleListQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new ScheduleListQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new ScheduleListQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new ScheduleListQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetScheduleId() {
    var query =
        new ScheduleListQuery().setScheduleId(SCHEDULE_ID.getOperator(), SCHEDULE_ID.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getScheduleId()).isNotNull();
    Assertions.assertThat(query.getScheduleId().getOperator()).isEqualTo(SCHEDULE_ID.getOperator());
    Assertions.assertThat(query.getScheduleId().getValue()).isEqualTo(SCHEDULE_ID.getValue());
  }

  @Test
  void shouldSetAccountId() {
    var query =
        new ScheduleListQuery().setAccountId(ACCOUNT_ID.getOperator(), ACCOUNT_ID.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getAccountId()).isNotNull();
    Assertions.assertThat(query.getAccountId().getOperator()).isEqualTo(ACCOUNT_ID.getOperator());
    Assertions.assertThat(query.getAccountId().getValue()).isEqualTo(ACCOUNT_ID.getValue());
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query =
        new ScheduleListQuery()
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .setScheduleId(SCHEDULE_ID.getOperator(), SCHEDULE_ID.getValue())
            .setAccountId(ACCOUNT_ID.getOperator(), ACCOUNT_ID.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl()).isEqualTo("https://example.com/api/v1/schedules");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "account.id", "schedule.id")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(ACCOUNT_ID.getOperator().getValue() + ":" + ACCOUNT_ID.getValue().toString()),
            List.of(
                SCHEDULE_ID.getOperator().getValue() + ":" + SCHEDULE_ID.getValue().toString()));
  }
}
