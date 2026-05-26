package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.query.NetworkSupplyQuery;
import java.time.Instant;
import java.util.List;

public class GetNetworkSupply {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query = new NetworkSupplyQuery().addTimestamp(QueryOperator.EQ, Instant.now());

    var supply1 = query.execute(client);
    IO.println("Supply: " + supply1);

    // Using client resource
    var supply2 =
        client
            .networks()
            .supplies()
            .timestamp(List.of(new CriteriaParam<>(QueryOperator.EQ, Instant.now())))
            .call();

    IO.println("Supply: " + supply2);
  }
}
