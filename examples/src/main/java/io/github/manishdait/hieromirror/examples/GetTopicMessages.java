package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.TopicMessageListQuery;

public class GetTopicMessages {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query =
        new TopicMessageListQuery().setTopicId("0.0.9074355").setLimit(10).setOrder(Order.ASC);

    var messages1 = query.execute(client);
    IO.println("Topic Messages: " + messages1);

    // Using client resource

    var messages2 = client.topics().findById("0.0.9074355").messages().call();

    IO.println("Topic Messages: " + messages2);
  }
}
