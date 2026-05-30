package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenType;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokenListQueryTest {
  private final int LIMIT = 10;
  private final Order ORDER = Order.DESC;
  private final PublicKey PUBLIC_KEY = PrivateKey.generateED25519().getPublicKey();
  private final CriteriaParam<AccountId> ACCOUNT_ID =
      new CriteriaParam<>(QueryOperator.EQ, new AccountId(0, 0, 1));
  private final CriteriaParam<TokenId> TOKEN_ID =
      new CriteriaParam<>(QueryOperator.EQ, new TokenId(0, 0, 1));
  private final String NAME = "TokenMon";
  private final List<TokenType> TOKEN_TYPES = List.of(TokenType.FUNGIBLE_COMMON);

  @Test
  void shouldCreateQueryWithDefaultValues() {
    var query = new TokenListQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.ASC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getPublicKey()).isNull();
    Assertions.assertThat(query.getAccountId()).isNull();
    Assertions.assertThat(query.getTokenId()).isNull();
    Assertions.assertThat(query.getName()).isNull();
    Assertions.assertThat(query.getTokenTypes()).isEmpty();
  }

  @Test
  void shouldSetOrder() {
    var query = new TokenListQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new TokenListQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new TokenListQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new TokenListQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetPublicKey() {
    var query = new TokenListQuery().setPublicKey(PUBLIC_KEY);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getPublicKey()).isNotNull();
    Assertions.assertThat(query.getPublicKey().toString()).isEqualTo(PUBLIC_KEY.toString());
  }

  @Test
  void shouldSetName() {
    var query = new TokenListQuery().setName(NAME);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getName()).isNotNull();
    Assertions.assertThat(query.getName()).isEqualTo(NAME);
  }

  @Test
  void shouldSetTokenTypes() {
    var query = new TokenListQuery().setTokenTypes(TOKEN_TYPES);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTokenTypes()).isNotEmpty();
    Assertions.assertThat(query.getTokenTypes()).isEqualTo(TOKEN_TYPES);
  }

  @Test
  void shouldAddSingleTokenType() {
    var query = new TokenListQuery();
    Assertions.assertThat(query.getTokenTypes()).isEmpty();

    query.addTokenType(TokenType.NON_FUNGIBLE_UNIQUE);
    Assertions.assertThat(query.getTokenTypes()).hasSize(1);
    Assertions.assertThat(query.getTokenTypes().getFirst())
        .isEqualTo(TokenType.NON_FUNGIBLE_UNIQUE);
  }

  @Test
  void shouldClearTokenTypes() {
    var query = new TokenListQuery().setTokenTypes(TOKEN_TYPES);
    Assertions.assertThat(query.getTokenTypes()).isNotEmpty();

    query.clearTokenTypes();
    Assertions.assertThat(query.getTokenTypes()).isEmpty();
  }

  @Test
  void shouldSetAccountId() {
    var query = new TokenListQuery().setAccountId(ACCOUNT_ID.getOperator(), ACCOUNT_ID.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getAccountId()).isNotNull();
    Assertions.assertThat(query.getAccountId().getOperator()).isEqualTo(ACCOUNT_ID.getOperator());
    Assertions.assertThat(query.getAccountId().getValue()).isEqualTo(ACCOUNT_ID.getValue());
  }

  @Test
  void shouldSetTokenId() {
    var query = new TokenListQuery().setTokenId(TOKEN_ID.getOperator(), TOKEN_ID.getValue());

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
        new TokenListQuery()
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .setName(NAME)
            .setTokenTypes(TOKEN_TYPES)
            .setTokenId(TOKEN_ID.getOperator(), TOKEN_ID.getValue())
            .setAccountId(ACCOUNT_ID.getOperator(), ACCOUNT_ID.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl()).isEqualTo("https://example.com/api/v1/tokens");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "name", "type", "token.id", "account.id")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
            List.of(NAME),
            TOKEN_TYPES.stream().map(t -> t.toString()).toList(),
            List.of(TOKEN_ID.getOperator().getValue() + ":" + TOKEN_ID.getValue().toString()),
            List.of(ACCOUNT_ID.getOperator().getValue() + ":" + ACCOUNT_ID.getValue().toString()));
  }
}
