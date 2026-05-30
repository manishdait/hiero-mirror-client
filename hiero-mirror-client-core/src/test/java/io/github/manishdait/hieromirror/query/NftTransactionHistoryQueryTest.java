package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NftTransactionHistoryQueryTest {
  private final NftId NFT_ID = new NftId(new TokenId(0, 0, 101), 1);
  private final int LIMIT = 10;
  private final Order ORDER = Order.DESC;
  private final List<CriteriaParam<Instant>> TIMESTAMPS =
      List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now()));

  @Test
  void shouldCreateQueryWithDefaultValues() {
    var query = new NftTransactionHistoryQuery();
    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getNftId()).isNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.DESC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.getTimestamps()).isEmpty();
  }

  @Test
  void shouldSetNftId() {
    var query = new NftQuery().setNftId(NFT_ID);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getNftId()).isNotNull();
    Assertions.assertThat(query.getNftId()).isEqualTo(NFT_ID);
  }

  @Test
  void shouldSetOrder() {
    var query = new NftTransactionHistoryQuery().setOrder(ORDER);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(ORDER);
  }

  @Test
  void shouldSetLimit() {
    var query = new NftTransactionHistoryQuery().setLimit(LIMIT);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getLimit()).isEqualTo(LIMIT);
  }

  @Test
  void shouldRaiseErrorWhenLimitIsGreaterThan100() {
    Assertions.assertThatThrownBy(() -> new NftTransactionHistoryQuery().setLimit(101))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldRaiseErrorWhenLimitIsLessThan1() {
    Assertions.assertThatThrownBy(() -> new NftTransactionHistoryQuery().setLimit(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("limit must be greater than 0 and less than 100");
  }

  @Test
  void shouldSetTimestamps() {
    var query = new NftTransactionHistoryQuery().setTimestamps(TIMESTAMPS);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).isNotEmpty();
    Assertions.assertThat(query.getTimestamps()).isEqualTo(TIMESTAMPS);
  }

  @Test
  void shouldAddTimestamp() {
    var query =
        new NftTransactionHistoryQuery()
            .setTimestamps(List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now())));

    query.addTimestamp(QueryOperator.EQ, Instant.now());

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getTimestamps()).hasSize(2);
  }

  @Test
  void shouldClearTimestamps() {
    var query = new NftTransactionHistoryQuery().setTimestamps(TIMESTAMPS);

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
        new NftTransactionHistoryQuery()
            .setNftId(NFT_ID)
            .setLimit(LIMIT)
            .setOrder(ORDER)
            .addTimestamp(timestamp1.getOperator(), timestamp1.getValue())
            .addTimestamp(timestamp2.getOperator(), timestamp2.getValue());

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo(
            "https://example.com/api/v1/tokens/"
                + NFT_ID.tokenId.toString()
                + "/nfts/"
                + NFT_ID.serial
                + "/transactions");
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams())
        .extractingByKeys("limit", "order", "timestamp")
        .contains(
            List.of(String.valueOf(LIMIT)),
            List.of(ORDER.getValue()),
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

    var query = new NftTransactionHistoryQuery().setLimit(LIMIT).setOrder(ORDER);

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("nftId must be set before executing query");
  }
}
