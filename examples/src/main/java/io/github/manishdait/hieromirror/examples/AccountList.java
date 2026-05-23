package io.github.manishdait.hieromirror.examples;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.NetworkType;
import io.github.manishdait.hieromirror.model.Operator;
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
