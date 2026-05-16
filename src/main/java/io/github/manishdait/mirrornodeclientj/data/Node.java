package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.FileId;
import com.hedera.hashgraph.sdk.Key;
import java.util.List;

public record Node(
    Key adminKey,
    List<Long> associatedRegisteredNodes,
    boolean declineReward,
    String description,
    FileId fileId,
    ServiceEndpoint grpcProxyEndpoint,
    Long maxStake,
    String memo,
    Long minStake,
    AccountId nodeAccountId,
    Long nodeId,
    String nodeCertHash,
    String publicKey,
    Long rewardRateStart,
    List<ServiceEndpoint> serviceEndpoints,
    Long stake,
    Long stakeNotRewarded,
    Long stakeRewarded,
    TimestampRange stakingPeriod,
    TimestampRange timestamp) {
  public record ServiceEndpoint(String domainName, String ipAddress, int port) {}
}
