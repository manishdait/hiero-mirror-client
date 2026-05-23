package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import java.time.Instant;
import java.util.List;

public record AccountInfo(
    AccountId accountId,
    String alias,
    Long autoRenewPeriod,
    AccountBalance balance,
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
    long pendingReward,
    List<Transaction> transactions) {}
