package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.Topic;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import tools.jackson.databind.JsonNode;

import java.util.Optional;

public class GetTopicByIdQuery extends Query<Optional<Topic>> {
  private final TopicId topicId;

  public GetTopicByIdQuery(MirrorNodeClient client, TopicId topicId) {
    super(client);
    this.topicId = topicId;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request = MirrorNodeRequest.newBuilder()
      .url(this.client.getBaseUrl() + "/api/v1/topics/" + topicId)
      .method("GET");

    return request.build();
  }

  @Override
  Optional<Topic> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTopic(node);
  }
}
