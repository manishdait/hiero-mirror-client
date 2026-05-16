package io.github.manishdait.mirrornodeclientj.internal.resource;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TopicResource;
import io.github.manishdait.mirrornodeclientj.query.TopicByIdQuery;
import org.jspecify.annotations.NonNull;

public class TopicResourceImpl implements TopicResource {
  private final MirrorNodeClient client;

  public TopicResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull TopicByIdQuery findById(TopicId topicId) {
    return new TopicByIdQuery(client, topicId);
  }
}
