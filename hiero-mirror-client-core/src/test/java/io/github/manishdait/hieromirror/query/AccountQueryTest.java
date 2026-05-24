package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.TransactionType;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountQueryTest {
  private final AccountId ID = new AccountId(0, 0, 2);
  private final Order ORDER = Order.ASC;
  private final int LIMIT = 10;
  private final boolean INCLUDE_TRANSACTION = false;
  private final TransactionType TRANSACTION_TYPE = TransactionType.CRYPTO_CREATE_ACCOUNT;
  private final List<CriteriaParam<Instant>> TIMESTAMPS =
      List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now()));

  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new AccountQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.DESC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getIncludeTransaction()).isTrue();
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
    var query = new AccountQuery().setTimestamps(TIMESTAMPS);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).isNotEmpty();
    Assertions.assertThat(query.getTimestamps()).isEqualTo(TIMESTAMPS);
  }

  @Test
  void shouldAddTimestamp() {
    var query =
        new AccountQuery()
            .setTimestamps(List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now())));

    query.addTimestamp(QueryOperator.EQ, Instant.now());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).hasSize(2);
  }

  @Test
  void shouldClearTimestamps() {
    var query = new AccountQuery().setTimestamps(TIMESTAMPS);

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
        new AccountQuery()
            .setAccountId(ID)
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .setIncludeTransaction(INCLUDE_TRANSACTION)
            .setTransactionType(TRANSACTION_TYPE)
            .addTimestamp(timestamp1.getOperator(), timestamp1.getValue())
            .addTimestamp(timestamp2.getOperator(), timestamp2.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo("https://example.com/api/v1/accounts/" + ID.toString());
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "transactions", "transactiontype", "timestamp")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(String.valueOf(INCLUDE_TRANSACTION)),
            List.of(TRANSACTION_TYPE.getValue()),
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

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query =
        new AccountQuery()
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .setIncludeTransaction(INCLUDE_TRANSACTION)
            .setTransactionType(TRANSACTION_TYPE);

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("account id or alias or evmAddress must be set before executing query");
  }
}
