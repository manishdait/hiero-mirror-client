package io.github.manishdait.hieromirror.examples;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.AccountQuery;

public class GetAccountById {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);
    var query =
        new AccountQuery()
            .setAccountId(AccountId.fromString("0.0.1"))
            .setIncludeTransaction(true)
            .setTransactionType(TransactionType.CONSENSUS_CREATE_TOPIC);

    var account1 = query.execute(client);
    IO.println("Account: " + account1);

    // Using client resource
    var account2 =
        client
            .accounts()
            .findById("0.0.1")
            .includeTransaction(true)
            .transactionType(TransactionType.CONSENSUS_CREATE_TOPIC)
            .call();
    IO.println("Account: " + account2);
  }
}
