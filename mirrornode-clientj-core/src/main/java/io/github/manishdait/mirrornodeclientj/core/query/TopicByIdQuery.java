package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.Topic;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class TopicByIdQuery extends Query<Optional<Topic>> {
  private final TopicId topicId;

  public TopicByIdQuery(MirrorNodeClient client, TopicId topicId) {
    super(client);
    this.topicId = topicId;
  }

  public TopicMessageBySequenceQuery messageBySequenceNumber(long sequenceNumber) {
    return new TopicMessageBySequenceQuery(client, topicId, sequenceNumber);
  }

  public TopicMessageByTimestampQuery messageByConsensusTimestamp(String timestamp) {
    return new TopicMessageByTimestampQuery(client, topicId, timestamp);
  }

  public TopicMessageListQuery messages() {
    return new TopicMessageListQuery(client, topicId);
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/topics/" + topicId)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<Topic> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTopic(node);
  }
}
