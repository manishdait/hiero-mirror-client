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

public class AccountTokenRelationshipInfoQueryTest {
  private final AccountId ID = new AccountId(0, 0, 2);
  private final Order ORDER = Order.DESC;
  private final int LIMIT = 10;
  private final CriteriaParam<TokenId> TOKEN_ID =
      new CriteriaParam<>(QueryOperator.EQ, new TokenId(0, 0, 1));

  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new AccountTokenRelationshipInfoQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.ASC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getTokenId()).isNull();
  }

  @Test
  void shouldSetAccountId() {
    var query = new AccountTokenRelationshipInfoQuery().setAccountId(ID);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getIdOrAliasOrEvmAddress()).isEqualTo(ID.toString());
  }

  @Test
  void shouldSetEvmAddress() {
    var evmAddress = PrivateKey.generateECDSA().getPublicKey().toEvmAddress();
    var query = new AccountTokenRelationshipInfoQuery().setEvmAddress(evmAddress);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getIdOrAliasOrEvmAddress()).isEqualTo(evmAddress.toString());
  }

  @Test
  void shouldSetAlias() {
    var alias = "HIQQEXWKW53RKN4W6XXC4Q232SYNZ3SZANVZZSUME5B5PRGXL663UAQA";
    var query = new AccountTokenRelationshipInfoQuery().setAlias(alias);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getIdOrAliasOrEvmAddress()).isEqualTo(alias);
  }

  @Test
  void shouldSetOrder() {
    var query = new AccountTokenRelationshipInfoQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new AccountTokenRelationshipInfoQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new AccountTokenRelationshipInfoQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new AccountTokenRelationshipInfoQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetTokenId() {
    var query =
        new AccountTokenRelationshipInfoQuery()
            .setTokenId(TOKEN_ID.getOperator(), TOKEN_ID.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTokenId()).isNotNull();
    Assertions.assertThat(query.getTokenId().getOperator()).isEqualTo(TOKEN_ID.getOperator());
    Assertions.assertThat(query.getTokenId().getValue()).isEqualTo(TOKEN_ID.getValue());
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query =
        new AccountTokenRelationshipInfoQuery()
            .setAccountId(ID)
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .setTokenId(TOKEN_ID.getOperator(), TOKEN_ID.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo("https://example.com/api/v1/accounts/" + ID.toString() + "/tokens");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "token.id")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(TOKEN_ID.getOperator().getValue() + ":" + TOKEN_ID.getValue().toString()));
  }

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query = new AccountTokenRelationshipInfoQuery().setLimit(LIMIT).setOrder(ORDER);

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("account id or alias or evmAddress must be set before executing query");
  }
}
