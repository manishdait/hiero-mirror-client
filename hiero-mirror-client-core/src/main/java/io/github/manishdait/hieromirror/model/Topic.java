package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.TopicId;
import java.time.Instant;
import java.util.List;

public record Topic(
    Key adminKey,
    AccountId autoRenewAccount,
    Long autoRenewPeriod,
    Instant createdTimestamp,
    // TODO: Custom Fee
    boolean deleted,
    List<Key> feeExemptKeyList,
    Key feeScheduleKey,
    String memo,
    Key submitKey,
    TimestampRange timestampRange,
    TopicId topicId) {}
