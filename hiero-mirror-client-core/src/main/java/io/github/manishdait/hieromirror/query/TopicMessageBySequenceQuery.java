package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.TopicMessage;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
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
  Optional<TopicMessage> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseTopicMessage(node);
  }
}
