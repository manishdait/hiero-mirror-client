package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.data.NetworkType;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MirrorNodeClientTest {
  @Test
  void shouldCallEndpoint() throws IOException, InterruptedException {
    MirrorNodeClient client = new MirrorNodeClient(NetworkType.TESTNET);
    var list = client.schedules().findById("0.0.15053").execute();

    System.out.println(list);
  }
}
