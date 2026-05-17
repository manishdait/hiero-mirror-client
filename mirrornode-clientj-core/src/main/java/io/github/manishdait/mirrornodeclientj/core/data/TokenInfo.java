package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenSupplyType;
import com.hedera.hashgraph.sdk.TokenType;
import java.time.Instant;

public record TokenInfo(
    Key adminKey,
    AccountId autoRenewAccount,
    Long autoRenewPeriod,
    Instant createdTimestamp,
    String decimals,
    boolean deleted,
    Long expiryTimestamp,
    Key feeScheduleKey,
    boolean freezeDefault,
    Key freezeKey,
    String initialSupply,
    Key kycKey,
    String maxSupply,
    byte[] metadata,
    Key metadataKey,
    Instant modifiedTimestamp,
    String name,
    String memo,
    Key pasueKey,
    TokenPauseStatus pauseStatus,
    Key supplyKey,
    TokenSupplyType supplyType,
    String symbol,
    TokenId tokenId,
    String totalSupply,
    AccountId treasuryAccountId,
    TokenType tokenType,
    Key wipeKey) {}
