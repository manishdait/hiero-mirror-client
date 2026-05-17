package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.ScheduleId;
import java.time.Instant;
import java.util.List;

public record ScheduleInfo(
    Key adminKey,
    Instant consensusTimestamp,
    AccountId creatorAccountId,
    boolean deleted,
    Instant executedTimestamp,
    Instant expirationTime,
    String memo,
    AccountId payerAccount,
    ScheduleId scheduleId,
    List<ScheduleSignatures> signatures,
    byte[] transactionBody,
    boolean waitForExpiry) {}
