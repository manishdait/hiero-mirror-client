package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.Status;
import java.time.Instant;
import java.util.List;

public record Transaction(
    Key batchKey,
    byte[] bytes,
    long chargedTxFee,
    Instant consensusTimestamp,
    AccountId entityId,
    List<CustomFee> maxCustomFees,
    String maxFee,
    byte[] memoBytes,
    TransactionType name,
    List<NftTransfer> nftTransfers,
    AccountId node,
    long nonce,
    Instant parentConsensusTimestamp,
    Status result,
    boolean scheduled,
    List<StakingRewardTransfer> stakingRewardTransfers,
    List<TokenTransfer> tokenTransfers,
    byte[] transactionHash,
    String transactionId,
    List<Transfer> transfers,
    String validDurationSeconds,
    Instant validStartTimestamp,
    List<AssessedCustomFee> assessedCustomFees) {}
