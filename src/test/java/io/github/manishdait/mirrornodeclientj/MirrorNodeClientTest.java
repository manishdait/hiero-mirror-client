package io.github.manishdait.mirrornodeclientj;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MirrorNodeClientTest {
  @Test
  void shouldCallEndpoint() throws IOException, InterruptedException {
    MirrorNodeClient client = new MirrorNodeClient(NetworkType.TESTNET);
    var list = client.account().findById("0.0.6105114").includeTransaction(false).execute();
    System.out.println(list);
    list.get().transactions().stream().forEach(t -> System.out.println(t.name()));

    var l2 = client.allowance().tokenAllowance("0.0.6105114").execute();
    System.out.println(l2);
  }
}
