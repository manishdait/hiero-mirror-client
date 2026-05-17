package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.TopicMessage;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class TopicMessageBySequenceQuery extends Query<Optional<TopicMessage>> {
  private final TopicId topicId;
  private final Long sequenceNumber;

  public TopicMessageBySequenceQuery(
      MirrorNodeClient client, TopicId topicId, long sequenceNumber) {
    super(client);
    this.topicId = topicId;
    this.sequenceNumber = sequenceNumber;
  }

  public TopicId getTopicId() {
    return topicId;
  }

  public Long getSequenceNumber() {
    return sequenceNumber;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(
                this.client.getBaseUrl()
                    + "/api/v1/topics/"
                    + topicId
                    + "/messages/"
                    + sequenceNumber)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<TopicMessage> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTopicMessage(node);
  }
}
