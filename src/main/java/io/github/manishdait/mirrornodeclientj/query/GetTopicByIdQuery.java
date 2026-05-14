package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.Topic;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class GetTopicByIdQuery extends Query<Optional<Topic>> {
  private final TopicId topicId;

  public GetTopicByIdQuery(MirrorNodeClient client, TopicId topicId) {
    super(client);
    this.topicId = topicId;
  }

  public GetTopicMessageBySequenceQuery messageBySequenceNumber(long sequenceNumber) {
    return new GetTopicMessageBySequenceQuery(client, topicId, sequenceNumber);
  }

  public GetTopicMessageByTimestampQuery messageByConsensusTimestamp(String timestamp) {
    return new GetTopicMessageByTimestampQuery(client, topicId, timestamp);
  }

  public GetTopicMessageListQuery messages() {
    return new GetTopicMessageListQuery(client, topicId);
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
