package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokenQueryTest {
  private final TokenId TOKEN_ID = new TokenId(0, 0, 101);
  private final CriteriaParam<Instant> TIMESTAMP =
      new CriteriaParam<>(QueryOperator.EQ, Instant.now());

  @Test
  void shouldCreateQueryWithDefaultValues() {
    var query = new TokenQuery();
    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTokenId()).isNull();
    Assertions.assertThat(query.getTimestamp()).isNull();
  }

  @Test
  void shouldSetTokenId() {
    var query = new TokenQuery().setTokenId(TOKEN_ID);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTokenId()).isNotNull();
    Assertions.assertThat(query.getTokenId()).isEqualTo(TOKEN_ID);
  }

  @Test
  void shouldSetTimestamp() {
    var query = new TokenQuery().setTimestamp(TIMESTAMP.getOperator(), TIMESTAMP.getValue());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamp()).isNotNull();
    Assertions.assertThat(query.getTimestamp().getOperator()).isEqualTo(TIMESTAMP.getOperator());
    Assertions.assertThat(query.getTimestamp().getValue()).isEqualTo(TIMESTAMP.getValue());
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query =
        new TokenQuery()
            .setTokenId(TOKEN_ID)
            .setTimestamp(TIMESTAMP.getOperator(), TIMESTAMP.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo("https://example.com/api/v1/tokens/" + TOKEN_ID.toString());
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("timestamp")
        .contains(
            List.of(
                TIMESTAMP.getOperator().getValue()
                    + ":"
                    + TIMESTAMP.getValue().getEpochSecond()
                    + "."
                    + TIMESTAMP.getValue().getNano()));
  }

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query = new TokenQuery().setTimestamp(TIMESTAMP.getOperator(), TIMESTAMP.getValue());

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("tokenId must be set before executing query");
  }
}
