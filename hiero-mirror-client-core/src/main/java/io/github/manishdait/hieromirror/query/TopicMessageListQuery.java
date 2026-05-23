package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.TopicMessage;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import org.jspecify.annotations.NonNull;
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
  Page<TopicMessage> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseTopicMessages(node);
  }
}
