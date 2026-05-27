# hiero-mirror-client

[![Maven Central](https://img.shields.io/maven-central/v/io.github.manishdait/hiero-mirror-client.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.manishdait/hiero-mirror-client-core)
[![Build Status](https://img.shields.io/github/actions/workflow/status/manishdait/hiero-mirror-client/maven.yml?branch=main)](https://github.com/manishdait/hiero-mirror-client/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Made with Java](https://img.shields.io/badge/Made%20with-Java-orange.svg?style=flat&logo=openjdk&logoColor=white)](https://www.java.com)

`hiero-mirror-client` is an unofficial Java client wrapper designed to effortlessly query the **Hiero Mirror Node REST API**. It integrates seamlessly with the official Hiero SDK classes (like `AccountId`) while providing a clean, fluent, builder-style interface to fetch network data.

> **Note:**
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
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        
        // Manual string concatenation and URL encoding for safety
        String url = "https://testnet.mirrornode.hedera.com/api/v1/accounts/0.0.1"
                + "?limit=25"
                + "&order=desc"
                + "&transactions=true"
                + "&transactiontype=NODECREATE"
                + "&timestamp=gte:1779909516.862059975";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Manual JSON parsing into custom classes
            ObjectMapper mapper = new ObjectMapper();
            AccountResponse account = mapper.readValue(response.body(), AccountResponse.class);
            System.out.println("Found Account: " + account.getAccountId());
        } else {
            System.err.println("API Error: Status Code " + response.statusCode());
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
  public static void main(String[] args) {
    // 1. Initialize the client for your target network
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    // Approach A: Using the explicit Query Object
    var query = new AccountQuery()
        .setAccountId(AccountId.fromString("0.0.1"))
        .setLimit(25)
        .setOrder(Order.DESC)
        .setIncludeTransaction(true)
        .setTransactionType(TransactionType.NODE_CREATE)
        .addTimestamp(QueryOperator.GTE, Instant.now());

    var account1 = query.execute(client);

    System.out.println("Account (Approach A): " + account1);

    // Approach B: Using the Client Resource API
    var account2 = client
        .accounts()
        .findByIdOrAliasOrEvmAddress("0.0.1")
        .limit(25)
        .order(Order.DESC)
        .includeTransaction(true)
        .transactionType(TransactionType.NODE_CREATE)
        .timestamp(List.of(new CriteriaParam<>(QueryOperator.GTE, Instant.now())))
        .call();

    System.out.println("Account (Approach B): " + account2);
  }
}
```



## Acknowledgments

This project is inspired by and modeled after the mirrornode data-access repositories established within the **Hiero Enterprise Java repositories** (such as their `AccountRepository` specification).

## License

This project is licensed under the MIT License see the [LICENSE](LICENSE) file for details.
