package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountCryptoAllowanceQueryTest {
  private final AccountId ID = new AccountId(0, 0, 2);
  private final Order ORDER = Order.ASC;
  private final int LIMIT = 10;
  private final CriteriaParam<AccountId> SPENDER_ID =
      new CriteriaParam<>(QueryOperator.EQ, new AccountId(0, 0, 1));

  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new AccountCryptoAllowanceQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.DESC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getSpenderId()).isNull();
  }

  @Test
  void shouldSetOrder() {
    var query = new AccountCryptoAllowanceQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new AccountCryptoAllowanceQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new AccountCryptoAllowanceQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new AccountCryptoAllowanceQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetSpenderAccountId() {
    var query =
        new AccountCryptoAllowanceQuery()
            .setSpenderId(SPENDER_ID.getOperator(), SPENDER_ID.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getSpenderId()).isNotNull();
    Assertions.assertThat(query.getSpenderId().getOperator()).isEqualTo(SPENDER_ID.getOperator());
    Assertions.assertThat(query.getSpenderId().getValue()).isEqualTo(SPENDER_ID.getValue());
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query =
        new AccountCryptoAllowanceQuery()
            .setAccountId(ID)
            .setOrder(ORDER)
            .setLimit(LIMIT)
            .setSpenderId(SPENDER_ID.getOperator(), SPENDER_ID.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo("https://example.com/api/v1/accounts/" + ID.toString() + "/allowances/crypto");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");
    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "spender.id")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(SPENDER_ID.getOperator().getValue() + ":" + SPENDER_ID.getValue().toString()));
  }

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query =
        new AccountCryptoAllowanceQuery()
            .setOrder(ORDER)
            .setLimit(LIMIT)
            .setSpenderId(SPENDER_ID.getOperator(), SPENDER_ID.getValue());

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("account id or alias or evmAddress must be set before executing query");
  }
}
