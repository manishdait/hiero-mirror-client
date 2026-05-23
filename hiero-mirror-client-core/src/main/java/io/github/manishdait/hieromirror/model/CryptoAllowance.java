package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;

public record CryptoAllowance(
    long amount,
    long amountGranted,
    AccountId owner,
    AccountId spender,
    TimestampRange timestamp) {}
