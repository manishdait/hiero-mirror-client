package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;

public record CryptoAllowance(
    long amount,
    long amountGranted,
    AccountId owner,
    AccountId spender,
    TimestampRange timestamp) {}
