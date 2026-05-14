package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.TopicMessage;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.util.List;
import tools.jackson.databind.JsonNode;

public class GetTopicMessageListQuery extends Query<List<TopicMessage>> {
  final TopicId topicId;

  public GetTopicMessageListQuery(MirrorNodeClient client, TopicId topicId) {
    super(client);
    this.topicId = topicId;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/topics/" + topicId + "/messages")
            .method("GET");

    return request.build();
  }

  @Override
  List<TopicMessage> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTopicMessages(node);
  }
}
