package io.github.manishdait.mirrornodeclientj.internal.resource;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TopicResource;
import io.github.manishdait.mirrornodeclientj.query.GetTopicByIdQuery;
import org.jspecify.annotations.NonNull;

public class TopicResourceImpl implements TopicResource {
  private final MirrorNodeClient client;

  public TopicResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull GetTopicByIdQuery findById(TopicId topicId) {
    return new GetTopicByIdQuery(client, topicId);
  }
}
