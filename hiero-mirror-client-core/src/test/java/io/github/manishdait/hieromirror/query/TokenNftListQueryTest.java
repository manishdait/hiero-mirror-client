package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokenNftListQueryTest {
  private final TokenId TOKEN_ID = new TokenId(0, 0, 101);
  private final int LIMIT = 10;
  private final Order ORDER = Order.DESC;
  private final CriteriaParam<AccountId> ACCOUNT_ID =
      new CriteriaParam<>(QueryOperator.EQ, new AccountId(0, 0, 1));
  private final CriteriaParam<Long> SERIAL = new CriteriaParam<>(QueryOperator.EQ, 1L);

  @Test
  void shouldCreateQueryWithDefaultValues() {
    var query = new TokenNftListQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.DESC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getAccountId()).isNull();
    Assertions.assertThat(query.getSerialNumber()).isNull();
    Assertions.assertThat(query.getTokenId()).isNull();
  }

  @Test
  void shouldSetTokenId() {
    var query = new TokenNftListQuery().setTokenId(TOKEN_ID);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTokenId()).isNotNull();
    Assertions.assertThat(query.getTokenId()).isEqualTo(TOKEN_ID);
  }

  @Test
  void shouldSetOrder() {
    var query = new TokenNftListQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new TokenNftListQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new TokenNftListQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new TokenNftListQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetAccountId() {
    var query =
        new TokenNftListQuery().setAccountId(ACCOUNT_ID.getOperator(), ACCOUNT_ID.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getAccountId()).isNotNull();
    Assertions.assertThat(query.getAccountId().getOperator()).isEqualTo(ACCOUNT_ID.getOperator());
    Assertions.assertThat(query.getAccountId().getValue()).isEqualTo(ACCOUNT_ID.getValue());
  }

  @Test
  void shouldSetSerial() {
    var query = new AccountNftListQuery().setSerialNumber(SERIAL.getOperator(), SERIAL.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getSerialNumber()).isNotNull();
    Assertions.assertThat(query.getSerialNumber().getOperator()).isEqualTo(SERIAL.getOperator());
    Assertions.assertThat(query.getSerialNumber().getValue()).isEqualTo(SERIAL.getValue());
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query =
        new TokenNftListQuery()
            .setTokenId(TOKEN_ID)
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .setAccountId(ACCOUNT_ID.getOperator(), ACCOUNT_ID.getValue())
            .setSerialNumber(SERIAL.getOperator(), SERIAL.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo("https://example.com/api/v1/tokens/" + TOKEN_ID.toString() + "/nfts");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "name", "serialnumber", "account.id")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(SERIAL.getOperator().getValue() + ":" + SERIAL.getValue().toString()),
            List.of(ACCOUNT_ID.getOperator().getValue() + ":" + ACCOUNT_ID.getValue().toString()));
  }

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query = new TokenNftListQuery().setLimit(LIMIT);

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("tokenId must be set before executing query");
  }
}
