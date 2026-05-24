package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.AccountCryptoAllowanceQuery;

public class GetAccountCryptoAllowance {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query =
        new AccountCryptoAllowanceQuery()
            .setAccountId("0.0.6105114")
            .setOrder(Order.ASC)
            .setLimit(10);

    var allowance1 = query.execute(client);
    IO.println("Allowance: " + allowance1.data());

    // Using client resource
    var allowance2 =
        client
            .accounts()
            .findByIdOrAliasOrEvmAddress("0.0.6105114")
            .cryptoAllowance()
            .order(Order.ASC)
            .limit(10)
            .call();

    IO.println("Allowance: " + allowance2.data());
  }
}
