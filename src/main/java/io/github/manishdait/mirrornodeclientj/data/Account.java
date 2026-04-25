package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import java.time.Instant;

public record Account(
    AccountId accountId,
    String alias,
    Long autoRenewPeriod,
    Instant createdTimestamp,
    boolean declineReward,
    boolean deleted,
    Long ethereumNonce,
    String evmAddress,
    Instant expiryTimestamp,
    Key key,
    int maxAutomaticTokenAssociations,
    String memo,
    boolean requireReceiverSignature,
    AccountId stakedAccountId,
    Long stakedNodeId,
    Instant stakePeriodStart,
    long pendingReward) {}
