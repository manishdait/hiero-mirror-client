package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.TopicMessage;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class TopicMessageByTimestampQuery extends Query<Optional<TopicMessage>> {
  private final TopicId topicId;
  private final String timestamp;

  public TopicMessageByTimestampQuery(MirrorNodeClient client, TopicId topicId, String timestamp) {
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
