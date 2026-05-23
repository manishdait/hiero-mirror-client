package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.AccountListQuery;

public class GetAccountList {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);
    var query = new AccountListQuery().setOrder(Order.ASC).setIncludeBalance(true);

    var accountList1 = query.execute(client);
    IO.println("AccountList: " + accountList1.data());

    // Using client resource
    var accountList2 = client.accounts().findAll().order(Order.ASC).includeBalance(true).call();
    IO.println("AccountList: " + accountList2.data());
  }
}
