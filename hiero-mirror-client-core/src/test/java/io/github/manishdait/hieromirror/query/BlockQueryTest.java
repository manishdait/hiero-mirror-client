package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BlockQueryTest {
  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new BlockQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getHashOrNumber()).isNull();
  }

  @Test
  void shouldSetBlockNumber() {
    var blockNumber = "1";
    var query = new BlockQuery().setNumber(blockNumber);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getHashOrNumber()).isEqualTo(blockNumber);
  }

  @Test
  void shouldSetBlockHash() {
    var blockHash =
        "0x3c08bbbee74d287b1dcd3f0ca6d1d2cb92c90883c4acf9747de9f3f3162ad25b999fc7e86699f60f2a3fb3ed9a646c6b";
    var query = new BlockQuery().setHash(blockHash);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getHashOrNumber()).isEqualTo(blockHash);
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var blockHash =
        "0x3c08bbbee74d287b1dcd3f0ca6d1d2cb92c90883c4acf9747de9f3f3162ad25b999fc7e86699f60f2a3fb3ed9a646c6b";
    var query = new BlockQuery().setHash(blockHash);

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo("https://example.com/api/v1/blocks/" + blockHash);
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams()).isEmpty();
  }

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query = new BlockQuery();

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("block hash or number must be set before executing the transaction");
  }
}
