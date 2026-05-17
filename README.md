# mirrornode-clientj

Mirrornode-ClientJ is a lightweight, Java client wrapper designed to easily query the **Hiero Mirror Node REST API**. It integrates seamlessly with the official Hiero SDK classes (like `AccountId`) while providing a clean, builder-style interface to fetch network data.

> ⚠️ **Project Status: Work In Progress**  
> This library is currently in early-stage development. No pre-built binaries or snapshot releases are available on Maven Central yet. To try it out, you will need to clone and build the library locally.
---

## Features

* **Network Native:** Supports Hiero networks (`MAINNET`, `TESTNET`, `PREVIEWNET`) right out of the box.
* **Fluent API:** Clean, declarative query building for accounts, topics, transactions, and more.
* **Type Safe:** Utilizes Hiero SDK primitives for predictable entity parsing.


## Local Setup & Installation

Since the project is not yet released, you need to compile and install it to your local Maven repository (`~/.m2/repository`) to use it in other test projects.

### Clone and Install Locally

Clone the repository and run the install command depending on your build system:
```bash
git clone https://github.com/manishdait/mirrornode-clientj.git
cd mirrornode-clientj
mvn clean install
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

## Acknowledgments

This project is architecturally inspired by and modeled after the mirrornode data-access paradigms established within the official **Hiero Enterprise Java repositories** (such as their `AccountRepository` specification).
