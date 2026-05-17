package io.github.manishdait.mirrornodeclientj.example;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.NetworkType;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import java.time.Duration;

public class AccountList {
  static void main() {
    var client = new MirrorNodeClient(NetworkType.TESTNET);
    var accounts =
        client
            .accounts()
            .findAll()
            .limit(2)
            .accountId(Operator.GTE, AccountId.fromString("0.0.2"))
            .execute(Duration.ofSeconds(10));

    IO.println("Account List: " + accounts.data());
  }
}
