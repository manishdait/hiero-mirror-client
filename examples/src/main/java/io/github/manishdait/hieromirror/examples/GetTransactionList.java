package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.BalanceModifier;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.TransactionListQuery;

public class GetTransactionList {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query =
        new TransactionListQuery()
            .setLimit(10)
            .setOrder(Order.ASC)
            .setTransactionType(TransactionType.CRYPTO_TRANSFER)
            .setBalanceModifier(BalanceModifier.CREDIT);

    var transactions1 = query.execute(client);
    IO.println("Transactions: " + transactions1);

    // Using client resource
    var transactions2 =
        client
            .transactions()
            .findAll()
            .limit(10)
            .order(Order.ASC)
            .transactionType(TransactionType.CRYPTO_TRANSFER)
            .type(BalanceModifier.CREDIT)
            .call();

    IO.println("Transactions: " + transactions2);
  }
}
