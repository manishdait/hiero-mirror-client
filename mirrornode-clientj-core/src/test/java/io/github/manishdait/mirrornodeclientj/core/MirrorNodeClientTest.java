package io.github.manishdait.mirrornodeclientj.core;

import io.github.manishdait.mirrornodeclientj.core.data.NetworkType;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MirrorNodeClientTest {
  @Test
  void shouldCallEndpoint() throws IOException, InterruptedException {
    MirrorNodeClient client = new MirrorNodeClient(NetworkType.TESTNET);
    var list = client.tokens().findNftsForAccountId("0.0.6105114").execute();

    System.out.println(list);
  }
}
