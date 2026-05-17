package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.Key;
import java.time.Instant;
import java.util.List;

public record RegisteredNode(
    Key adminKey,
    Instant createdTimestamp,
    String description,
    long registeredNodeId,
    List<ServiceEndpoint> serviceEndpoint,
    TimestampRange timestamp) {
  public record ServiceEndpoint(
      String domainName,
      String ipAddress,
      int port,
      boolean requireTls,
      RegisteredServiceType type,
      BlockNodeEndpoint blockNode,
      GeneralServiceEndpoint generalService,
      MirrorNodeEndpoint mirrorNode,
      RpcRelayEndpoint rpcRelay) {}
}
