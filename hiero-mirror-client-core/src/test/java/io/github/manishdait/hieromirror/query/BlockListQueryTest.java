package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BlockListQueryTest {
  private final Order ORDER = Order.DESC;
  private final int LIMIT = 10;
  private final CriteriaParam<Long> BLOCK_NUMBER = new CriteriaParam<>(QueryOperator.EQ, 1L);
  private final List<CriteriaParam<Instant>> TIMESTAMPS =
      List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now()));

  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new BlockListQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.DESC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getTimestamps()).isEmpty();
    Assertions.assertThat(query.getBlockNumber()).isNull();
  }

  @Test
  void shouldSetOrder() {
    var query = new BlockListQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new BlockListQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new BlockListQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new BlockListQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetBlockNumber() {
    var query =
        new BlockListQuery().setBlockNumber(BLOCK_NUMBER.getOperator(), BLOCK_NUMBER.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getBlockNumber()).isNotNull();
    Assertions.assertThat(query.getBlockNumber().getOperator())
        .isEqualTo(BLOCK_NUMBER.getOperator());
    Assertions.assertThat(query.getBlockNumber().getValue()).isEqualTo(BLOCK_NUMBER.getValue());
  }

  @Test
  void shouldSetTimestamps() {
    var query = new BlockListQuery().setTimestamps(TIMESTAMPS);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).isNotEmpty();
    Assertions.assertThat(query.getTimestamps()).isEqualTo(TIMESTAMPS);
  }

  @Test
  void shouldAddTimestamp() {
    var query =
        new BlockListQuery()
            .setTimestamps(List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now())));

    query.addTimestamp(QueryOperator.EQ, Instant.now());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).hasSize(2);
  }

  @Test
  void shouldClearTimestamps() {
    var query = new BlockListQuery().setTimestamps(TIMESTAMPS);

    query.clearTimestamps();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).isEmpty();
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var timestamp1 = new CriteriaParam<>(QueryOperator.EQ, Instant.now().plusSeconds(10));
    var timestamp2 = new CriteriaParam<>(QueryOperator.GTE, Instant.now().minusSeconds(10));

    var query =
        new BlockListQuery()
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .setBlockNumber(BLOCK_NUMBER.getOperator(), BLOCK_NUMBER.getValue())
            .addTimestamp(timestamp1.getOperator(), timestamp1.getValue())
            .addTimestamp(timestamp2.getOperator(), timestamp2.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl()).isEqualTo("https://example.com/api/v1/blocks");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "block.number", "timestamp")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(
                BLOCK_NUMBER.getOperator().getValue() + ":" + BLOCK_NUMBER.getValue().toString()),
            List.of(
                timestamp1.getOperator().getValue()
                    + ":"
                    + timestamp1.getValue().getEpochSecond()
                    + "."
                    + timestamp1.getValue().getNano(),
                timestamp2.getOperator().getValue()
                    + ":"
                    + timestamp2.getValue().getEpochSecond()
                    + "."
                    + timestamp2.getValue().getNano()));
  }
}
