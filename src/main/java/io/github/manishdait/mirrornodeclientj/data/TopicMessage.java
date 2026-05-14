package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TopicId;
import java.time.Instant;

public record TopicMessage(
    // TODO: Chunk info
    Instant consensusTimestamp,
    String message,
    AccountId payerAccountId,
    byte[] runningHash,
    int runningHashVersion,
    long sequenceNumber,
    TopicId topic_id) {}
