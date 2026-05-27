package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.query.TopicQuery;

public class GetTopic {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query = new TopicQuery().setTopicId("0.0.9074355");

    var topic1 = query.execute(client);
    IO.println("Topic: " + topic1);

    // Using client resource

    var topic2 = client.topics().findById("0.0.9074355").call();

    IO.println("Topic: " + topic2);
  }
}
