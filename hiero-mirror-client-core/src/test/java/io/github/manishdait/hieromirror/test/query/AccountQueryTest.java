package io.github.manishdait.hieromirror.test.query;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.AccountQuery;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountQueryTest {
  private final Order ORDER = Order.ASC;
  private final int LIMIT = 10;
  private final boolean INCLUDE_TRANSACTION = true;
  private final TransactionType TRANSACTION_TYPE = TransactionType.CRYPTO_CREATE_ACCOUNT;
  private final List<CriteriaParam<Instant>> TIMESTAMPS =
      List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now()));

  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new AccountQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.DESC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getIncludeTransaction()).isFalse();
    Assertions.assertThat(query.getTransactionType()).isNull();
    Assertions.assertThat(query.getTimestamps()).isEmpty();
  }

  @Test
  void shouldSetOrder() {
    var query = new AccountQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new AccountQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new AccountQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new AccountQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetIncludeTransaction() {
    var query = new AccountQuery().setIncludeTransaction(INCLUDE_TRANSACTION);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getIncludeTransaction()).isEqualTo(INCLUDE_TRANSACTION);
  }

  @Test
  void shouldSetTransactionType() {
    var query = new AccountQuery().setTransactionType(TRANSACTION_TYPE);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTransactionType()).isNotNull();
    Assertions.assertThat(query.getTransactionType()).isEqualTo(TRANSACTION_TYPE);
  }

  @Test
  void shouldSetTimestamps() {
    var query = new AccountQuery().setTimestamp(TIMESTAMPS);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).isNotEmpty();
    Assertions.assertThat(query.getTimestamps()).isEqualTo(TIMESTAMPS);
  }

  @Test
  void shouldAddTimestamp() {
    var query =
        new AccountQuery()
            .setTimestamp(List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now())));

    query.addTimestamp(QueryOperator.EQ, Instant.now());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).hasSize(2);
  }

  @Test
  void shouldClearTimestamps() {
    var query = new AccountQuery().setTimestamp(TIMESTAMPS);

    query.clearTimestamps();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).isEmpty();
  }
}
