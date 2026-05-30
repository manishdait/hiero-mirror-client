package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NftQueryTest {
  @Test
  void shouldCreateQueryWithDefaultValues() {
    var query = new NftQuery();
    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getNftId()).isNull();
  }

  @Test
  void shouldSetNftId() {
    var nftId = new NftId(new TokenId(0, 0, 101), 1);
    var query = new NftQuery().setNftId(nftId);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getNftId()).isNotNull();
    Assertions.assertThat(query.getNftId()).isEqualTo(nftId);
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var nftId = new NftId(new TokenId(0, 0, 101), 1);

    var query = new NftQuery().setNftId(nftId);

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo(
            "https://example.com/api/v1/tokens/"
                + nftId.tokenId.toString()
                + "/nfts/"
                + nftId.serial);
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams()).isEmpty();
  }

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query = new NftQuery();

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("nftId must be set before executing query");
  }
}
