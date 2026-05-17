package io.github.manishdait.mirrornodeclientj.example;

import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.NetworkType;

public class AccountById {
  static void main() {
    var client = new MirrorNodeClient(NetworkType.TESTNET);

    var account = client.accounts().findById("0.0.2").includeTransaction(true).execute();

    account.ifPresent(accountInfo -> IO.println("Account: " + accountInfo));
  }
}
