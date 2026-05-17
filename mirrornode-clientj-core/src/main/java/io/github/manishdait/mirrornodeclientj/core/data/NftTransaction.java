package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;
import java.time.Instant;

public record NftTransaction(
    Instant consensusTimestamp,
    boolean isApproval,
    long nonce,
    AccountId receiverAccountId,
    AccountId senderAccountId,
    String transactionId,
    TransactionType type) {}
