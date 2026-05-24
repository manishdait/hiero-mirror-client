package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.BlockListQuery;

public class GetBlocks {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query = new BlockListQuery().setOrder(Order.ASC).setLimit(10);

    var blocks1 = query.execute(client);
    IO.println("Blocks: " + blocks1.data());

    var blocks2 = client.blocks().findAll().order(Order.ASC).limit(10).call();

    IO.println("Blocks: " + blocks2.data());
  }
}
