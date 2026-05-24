package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountListQueryTest {
  private final int LIMIT = 10;
  private final Order ORDER = Order.DESC;
  private final boolean INCLUDE_BALANCE = false;
  private final PublicKey PUBLIC_KEY = PrivateKey.generateED25519().getPublicKey();
  private final CriteriaParam<AccountId> ACCOUNT_ID =
      new CriteriaParam<>(QueryOperator.EQ, new AccountId(0, 0, 1));
  private final CriteriaParam<Hbar> BALANCE = new CriteriaParam<>(QueryOperator.EQ, Hbar.from(1));

  @Test
  void shouldCreateQueryWithDefaultValues() {
    var query = new AccountListQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.ASC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getIncludeBalance()).isTrue();

    Assertions.assertThat(query.getPublicKey()).isNull();
    Assertions.assertThat(query.getAccountId()).isNull();
    Assertions.assertThat(query.getBalance()).isNull();
  }

  @Test
  void shouldSetOrder() {
    var query = new AccountListQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new AccountListQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new AccountListQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new AccountListQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetIncludeBalance() {
    var query = new AccountListQuery().setIncludeBalance(INCLUDE_BALANCE);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getIncludeBalance()).isEqualTo(INCLUDE_BALANCE);
  }

  @Test
  void shouldSetPublicKey() {
    var query = new AccountListQuery().setPublicKey(PUBLIC_KEY);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getPublicKey()).isNotNull();
    Assertions.assertThat(query.getPublicKey().toString()).isEqualTo(PUBLIC_KEY.toString());
  }

  @Test
  void shouldSetAccountId() {
    var query =
        new AccountListQuery().setAccountId(ACCOUNT_ID.getOperator(), ACCOUNT_ID.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getAccountId()).isNotNull();
    Assertions.assertThat(query.getAccountId().getOperator()).isEqualTo(ACCOUNT_ID.getOperator());
    Assertions.assertThat(query.getAccountId().getValue()).isEqualTo(ACCOUNT_ID.getValue());
  }

  @Test
  void shouldSetBalance() {
    var query = new AccountListQuery().setBalance(BALANCE.getOperator(), BALANCE.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getBalance()).isNotNull();
    Assertions.assertThat(query.getBalance().getOperator()).isEqualTo(BALANCE.getOperator());
    Assertions.assertThat(query.getBalance().getValue()).isEqualTo(BALANCE.getValue());
  }

  @Test
  void shouldBuildRequestFromAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query =
        new AccountListQuery()
            .setOrder(ORDER)
            .setLimit(LIMIT)
            .setIncludeBalance(INCLUDE_BALANCE)
            .setPublicKey(PUBLIC_KEY)
            .setAccountId(ACCOUNT_ID.getOperator(), ACCOUNT_ID.getValue())
            .setBalance(BALANCE.getOperator(), BALANCE.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl()).isEqualTo("https://example.com/api/v1/accounts");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");
    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys(
            "limit", "order", "balance", "account.publickey", "account.id", "account.balance")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(String.valueOf(INCLUDE_BALANCE)),
            List.of(PUBLIC_KEY.toString()),
            List.of(ACCOUNT_ID.getOperator().getValue() + ":" + ACCOUNT_ID.getValue().toString()),
            List.of(
                BALANCE.getOperator().getValue()
                    + ":"
                    + String.valueOf(BALANCE.getValue().toTinybars())));
  }
}
