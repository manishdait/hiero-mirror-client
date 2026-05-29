package io.github.manishdait.hieromirror.integration;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;
import java.util.List;
import java.util.Map;

public class IntegrationTestEnv {
  static final String CONSENSUS_NODE_ENDPOINT = "127.0.0.1:50211";
  public static final String MIRROR_NODE_GRPC_ENDPOINT = "127.0.0.1:5600";
  static final AccountId CONSENSUS_NODE_ACCOUNT_ID = new AccountId(0, 0, 3);

  public final Client client;
  public PublicKey operatorKey;
  public AccountId operatorId;

  public IntegrationTestEnv() {
    var networkType = System.getProperty("NETWORK");

    if (networkType == null) {
      throw new RuntimeException("NETWORK system property is not set");
    }

    try {
      if (networkType.equals("testnet")) {
        client = Client.forTestnet();
      } else if (networkType.equals("solo")) {
        var network = Map.of(CONSENSUS_NODE_ENDPOINT, CONSENSUS_NODE_ACCOUNT_ID);
        client = Client.forNetwork(network).setMirrorNetwork(List.of(MIRROR_NODE_GRPC_ENDPOINT));

        var operatorPrivateKey = PrivateKey.fromString(System.getProperty("OPERATOR_KEY"));
        operatorId = AccountId.fromString(System.getProperty("OPERATOR_ID"));
        operatorKey = operatorPrivateKey.getPublicKey();

        client.setOperator(operatorId, operatorPrivateKey);
      } else {
        throw new RuntimeException("Unable to configure the suitable client");
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize Integration Test Environment", e);
    }
  }

  public void close() throws Exception {
    client.close();
  }
}
