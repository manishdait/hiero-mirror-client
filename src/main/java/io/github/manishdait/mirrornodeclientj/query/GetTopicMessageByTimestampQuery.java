package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.TopicMessage;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class GetTopicMessageByTimestampQuery extends Query<Optional<TopicMessage>> {
  private final TopicId topicId;
  private final String timestamp;

  public GetTopicMessageByTimestampQuery(
      MirrorNodeClient client, TopicId topicId, String timestamp) {
    super(client);
    this.topicId = topicId;
    this.timestamp = timestamp;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/topics/" + topicId + "/messages/" + timestamp)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<TopicMessage> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTopicMessage(node);
  }
}
