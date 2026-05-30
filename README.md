# hiero-mirror-client

[![Maven Central](https://img.shields.io/maven-central/v/io.github.manishdait/hiero-mirror-client.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.manishdait/hiero-mirror-client-core)
[![Build Status](https://img.shields.io/github/actions/workflow/status/manishdait/hiero-mirror-client/maven.yml?branch=main&label=Build)](https://github.com/manishdait/hiero-mirror-client/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-25%2B-blue.svg)](https://openjdk.org/)
[![Made with Java](https://img.shields.io/badge/Made%20with-Java-orange.svg?style=flat&logo=openjdk&logoColor=white)](https://www.java.com)

`hiero-mirror-client` is an unofficial Java client wrapper designed to effortlessly query the **Hiero Mirror Node REST API**. It integrates seamlessly with the official Hiero SDK classes (like `AccountId`) while providing a clean, fluent, builder-style interface to fetch network data.

> [!NOTE]
> This is initial release focused on REST query endpoints (`GET` requests).
> Support for `POST` requests and `Contract` endpoint will be added in upcoming version.

## Why Use This?

Manually constructing complex API queries can be tedious and error-prone. For instance, consider this standard Mirror Node REST URL containing multiple query parameters:

```
https://testnet.mirrornode.hedera.com/api/v1/accounts/0.0.1?limit=25&order=desc&transactions=true&transactiontype=NODECREATE&timestamp=gte:34343434.43434](https://testnet.mirrornode.hedera.com/api/v1/accounts/0.0.1?limit=25&order=desc&transactions=true&transactiontype=NODECREATE&timestamp=gte:34343434.43434
```

You have to write painful boilerplate code like below just to parse a single endpoint:

```java
public class ManualRestCall {
    static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        
        var url = "https://testnet.mirrornode.hedera.com/api/v1/accounts/0.0.1"
                + "?limit=25"
                + "&order=desc"
                + "&transactions=true"
                + "&transactiontype=NODECREATE"
                + "&timestamp=gte:1779909516.862059975";

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Manual JSON parsing into custom classes
            var mapper = new ObjectMapper();
            var account = mapper.readValue(response.body(), AccountResponse.class);
            IO.println("Found Account: " + account.getAccountId());
        } else {
            IO.println("API Error: Status Code " + response.statusCode());
        }
    }
}
```

Instead of handling raw HTTP clients, dealing with manual URL encoding, or parsing raw JSON strings, `hiero-mirror-client` abstracts everything into a **type-safe, fluent Java builder API**.

## Installation

Add the following dependency to your build configuration:
```xml
<dependency>
    <groupId>io.github.manishdait</groupId>
    <artifactId>hiero-mirror-client-core</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage Examples

Here is how you can completely eliminate manual URL string formatting and execute queries using the fluent API interface.

### Approach A: Using the explicit Query Object

```java
package io.github.manishdait.hieromirror.examples;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.AccountQuery;

import java.time.Instant;
import java.util.List;

public class Test {
  static void main(String[] args) {
    // 1. Initialize the client for your target network
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query = new AccountQuery()
        .setAccountId(AccountId.fromString("0.0.1"))
        .setLimit(25)
        .setOrder(Order.DESC)
        .setIncludeTransaction(true)
        .setTransactionType(TransactionType.NODE_CREATE)
        .addTimestamp(QueryOperator.GTE, Instant.now());

    var account = query.execute(client);

    IO.println("Account (Approach A): " + account);
  }
}
```

### Approach B: Using the Client Resource API

```java
package io.github.manishdait.hieromirror.examples;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.TransactionType;

import java.time.Instant;
import java.util.List;

public class Test {
  static void main(String[] args) {
    // 1. Initialize the client for your target network
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var account = client
        .accounts()
        .findByIdOrAliasOrEvmAddress("0.0.1")
        .limit(25)
        .order(Order.DESC)
        .includeTransaction(true)
        .transactionType(TransactionType.NODE_CREATE)
        .timestamp(List.of(new CriteriaParam<>(QueryOperator.GTE, Instant.now())))
        .call();

    IO.println("Account (Approach B): " + account);
  }
}
```

## Acknowledgments

This project is inspired by and modeled after the mirrornode data-access repositories established within the **Hiero Enterprise Java repositories** (such as their `AccountRepository` specification).

## License

This project is licensed under the MIT License see the [LICENSE](LICENSE) file for details.
