package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.NetworkType;

public class AccountById {
  static void main() {
    var client = new MirrorNodeClient(NetworkType.TESTNET);

    var account = client.accounts().findById("0.0.2").includeTransaction(true).execute();

    account.ifPresent(accountInfo -> IO.println("Account: " + accountInfo));
  }
}
