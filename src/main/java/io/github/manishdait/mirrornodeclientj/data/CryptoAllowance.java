package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;

public record CryptoAllowance(
    long amount,
    long amountGranted,
    AccountId owner,
    AccountId spender,
    TimestampRange timestamp) {}
