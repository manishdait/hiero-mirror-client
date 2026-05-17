package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.Page;
import io.github.manishdait.mirrornodeclientj.core.data.TopicMessage;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import tools.jackson.databind.JsonNode;

public class TopicMessageListQuery extends Query<Page<TopicMessage>> {
  final TopicId topicId;

  public TopicMessageListQuery(MirrorNodeClient client, TopicId topicId) {
    super(client);
    this.topicId = topicId;
  }

  public TopicId getTopicId() {
    return topicId;
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
  Page<TopicMessage> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTopicMessages(node);
  }
}
