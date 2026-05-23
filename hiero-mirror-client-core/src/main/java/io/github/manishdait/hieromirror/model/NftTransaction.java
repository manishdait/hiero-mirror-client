package io.github.manishdait.hieromirror.model;

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
