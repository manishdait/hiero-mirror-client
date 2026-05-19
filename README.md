# mirrornode-clientj

Mirrornode-ClientJ is a lightweight, Java client wrapper designed to easily query the **Hiero Mirror Node REST API**. It integrates seamlessly with the official Hiero SDK classes (like `AccountId`) while providing a clean, builder-style interface to fetch network data.

> ⚠️ **Project Status: Initial Release**  
> While the first stable artifact is now published to Maven Central, please note that the API is still maturing and may undergo breaking updates prior to the v1.0.0 release.
---

## Features

* **Network Native:** Supports Hiero networks (`MAINNET`, `TESTNET`, `PREVIEWNET`) right out of the box.
* **Fluent API:** Clean, declarative query building for accounts, topics, transactions, and more.
* **Type Safe:** Utilizes Hiero SDK primitives for predictable entity parsing.


## Installation

To include the release of the library in your project, add the following dependency to your build configuration.

### Maven (`pom.xml`)
```xml
<dependency>
  <groupId>io.github.manishdait</groupId>
  <artifactId>mirrornode-clientj-core</artifactId>
  <version>0.0.1</version>
</dependency>
```

## Usage Examples

### 1. Fetching a List of Accounts (With Query Filters)

An example of querying accounts list using condition operators, limits, and customizable timeouts.

```java
import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.NetworkType;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import java.time.Duration;

public class AccountList {
    public static void main(String[] args) {
        // Initialize client for Hiero Testnet
        var client = new MirrorNodeClient(NetworkType.TESTNET);

        // Build and execute the query
        var accounts = client.accounts()
            .findAll()
            .limit(2)
            .accountId(Operator.GTE, AccountId.fromString("0.0.2"))
            .execute(Duration.ofSeconds(10));

        System.out.println("Account List: " + accounts.data());
    }
}
```

### 2. Fetching a Specific Account by ID

An example to query an account directly by its string ID and choose whether to include transactional history details in the response.

```java
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.NetworkType;

public class AccountById {
    public static void main(String[] args) {
        // Initialize client
        var client = new MirrorNodeClient(NetworkType.TESTNET);

        // Query a single account entity
        var account = client.accounts()
            .findById("0.0.2")
            .includeTransaction(true)
            .execute();

        // Handle the Optional response safely
        account.ifPresent(accountInfo -> System.out.println("Account: " + accountInfo));
    }
}
```

### 3. Alternative Query Approach (Direct Instance Instantiation)

Alternatively, you can instantiate the query object directly instead of using the client factory methods.

```java
import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.NetworkType;
import io.github.manishdait.mirrornodeclientj.core.data.TransactionType;
import io.github.manishdait.mirrornodeclientj.core.query.AccountByIdQuery; // Adjust import path as necessary

public class AccountAlternativeQuery {
    public static void main(String[] args) {
        var client = new MirrorNodeClient(NetworkType.TESTNET);

        // Directly instantiate the query builder
        var query = new AccountByIdQuery(client, AccountId.fromString("0.0.2"))
            .limit(10)
            .includeTransaction(true)
            .transactionType(TransactionType.ETHEREUM_TRANSACTION);

        var account = query.execute();
        account.ifPresent(accountInfo -> System.out.println("Account: " + accountInfo));
    }
}
```

## Local Setup & Installation

If you want to modify the library or build it from the source locally, compile and install it to your local Maven repository (`~/.m2/repository`):

```bash
git clone https://github.com/manishdait/mirrornode-clientj.git
cd mirrornode-clientj
mvn clean install
```




## Acknowledgments

This project is architecturally inspired by and modeled after the mirrornode data-access paradigms established within the official **Hiero Enterprise Java repositories** (such as their `AccountRepository` specification).

## License

This project is licensed under the MIT License see the [LICENSE](LICENSE) file for details.
