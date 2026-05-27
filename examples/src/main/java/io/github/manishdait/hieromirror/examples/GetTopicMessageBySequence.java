package io.github.manishdait.hieromirror.examples;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.query.TopicMessageQuery;

public class GetTopicMessageBySequence {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query = new TopicMessageQuery().setTopicId("0.0.9074355").setSequenceNumber(1L);

    var message1 = query.execute(client);
    IO.println("Topic Message: " + message1);

    // Using client resource

    var message2 = client.topics().findById("0.0.9074355").messageBySequence(1L).call();

    IO.println("Topic Message: " + message2);
  }
}
