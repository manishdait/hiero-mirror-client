package io.github.manishdait.mirrornodeclientj;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MirrorNodeClientTest {
  @Test
  void shouldCallEndpoint() throws IOException, InterruptedException {
    MirrorNodeClient client = new MirrorNodeClient(NetworkType.TESTNET);
    var list = client.topicResource().findById("0.0.8960793").execute();

    System.out.println(list);
  }
}
