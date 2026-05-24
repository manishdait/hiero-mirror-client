# hiero-mirror-client

`hiero-mirror-client` is an un-official Java client wrapper designed to easily query the **Hiero Mirror Node REST API**. It integrates seamlessly with the official Hiero SDK classes (like `AccountId`) while providing a clean, builder-style interface to fetch network data.


## Usage Examples

```java
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
            .findByIdOrAliasOrEvmAddress("0.0.1")
            .includeTransaction(true)
            .transactionType(TransactionType.CONSENSUS_CREATE_TOPIC)
            .call();
    IO.println("Account: " + account2);
  }
}
```



## Acknowledgments

This project is inspired by and modeled after the mirrornode data-access repositories established within the **Hiero Enterprise Java repositories** (such as their `AccountRepository` specification).

## License

This project is licensed under the MIT License see the [LICENSE](LICENSE) file for details.
