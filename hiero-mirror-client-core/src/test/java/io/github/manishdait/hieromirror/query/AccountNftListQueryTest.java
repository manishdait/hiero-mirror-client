package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountNftListQueryTest {
  private final AccountId ID = new AccountId(0, 0, 2);
  private final Order ORDER = Order.DESC;
  private final int LIMIT = 10;
  private final CriteriaParam<TokenId> TOKEN_ID =
      new CriteriaParam<>(QueryOperator.EQ, new TokenId(0, 0, 1));
  private final CriteriaParam<AccountId> SPENDER_ID =
      new CriteriaParam<>(QueryOperator.EQ, new AccountId(0, 0, 1));
  private final CriteriaParam<Long> SERIAL = new CriteriaParam<>(QueryOperator.EQ, 1L);

  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new AccountNftListQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.DESC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getSerialNumber()).isNull();
    Assertions.assertThat(query.getTokenId()).isNull();
    Assertions.assertThat(query.getSpenderId()).isNull();
    Assertions.assertThat(query.getIdOrAliasOrEvmAddress()).isNull();
  }

  @Test
  void shouldSetAccountId() {
    var query = new AccountNftListQuery().setAccountId(ID);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getIdOrAliasOrEvmAddress()).isEqualTo(ID.toString());
  }

  @Test
  void shouldSetEvmAddress() {
    var evmAddress = PrivateKey.generateECDSA().getPublicKey().toEvmAddress();
    var query = new AccountNftListQuery().setEvmAddress(evmAddress);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getIdOrAliasOrEvmAddress()).isEqualTo(evmAddress.toString());
  }

  @Test
  void shouldSetAlias() {
    var alias = "HIQQEXWKW53RKN4W6XXC4Q232SYNZ3SZANVZZSUME5B5PRGXL663UAQA";
    var query = new AccountNftListQuery().setAlias(alias);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getIdOrAliasOrEvmAddress()).isEqualTo(alias);
  }

  @Test
  void shouldSetOrder() {
    var query = new AccountNftListQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new AccountNftListQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
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
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new AccountNftListQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new AccountNftListQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetTokenId() {
    var query = new AccountNftListQuery().setTokenId(TOKEN_ID.getOperator(), TOKEN_ID.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTokenId()).isNotNull();
    Assertions.assertThat(query.getTokenId().getOperator()).isEqualTo(TOKEN_ID.getOperator());
    Assertions.assertThat(query.getTokenId().getValue()).isEqualTo(TOKEN_ID.getValue());
  }

  @Test
  void shouldSetSpenderId() {
    var query =
        new AccountNftListQuery().setSpenderId(SPENDER_ID.getOperator(), SPENDER_ID.getValue());

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
        new AccountNftListQuery()
            .setAccountId(ID)
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .setSerialNumber(SERIAL.getOperator(), SERIAL.getValue())
            .setTokenId(TOKEN_ID.getOperator(), TOKEN_ID.getValue())
            .setSpenderId(SPENDER_ID.getOperator(), SPENDER_ID.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo("https://example.com/api/v1/accounts/" + ID.toString() + "/nfts");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "token.id", "spender.id", "serialnumber")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(TOKEN_ID.getOperator().getValue() + ":" + TOKEN_ID.getValue().toString()),
            List.of(SPENDER_ID.getOperator().getValue() + ":" + SPENDER_ID.getValue().toString()),
            List.of(SERIAL.getOperator().getValue() + ":" + SERIAL.getValue().toString()));
  }

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query = new AccountNftListQuery().setLimit(LIMIT).setOrder(ORDER);

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("account id or alias or evmAddress must be set before executing query");
  }
}
