package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import java.time.Instant;

public record Nft(
    AccountId accountId,
    Instant createdTimestamp,
    AccountId delegatingSpender,
    boolean deleted,
    byte[] metadata,
    Instant modifiedTimestamp,
    long serialNumber,
    AccountId spenderId,
    TokenId tokenId) {}
