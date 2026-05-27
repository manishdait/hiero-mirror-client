package io.github.manishdait.hieromirror.internal.resource;

import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.TopicRequestImpl;
import io.github.manishdait.hieromirror.resource.TopicResource;
import io.github.manishdait.hieromirror.resource.wrapper.TopicRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class TopicResourceImpl implements TopicResource {
  private final MirrorNodeClient client;

  public TopicResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull TopicRequest findById(@NonNull TopicId topicId) {
    Objects.requireNonNull(topicId, "topicId must not be null");
    return new TopicRequestImpl(client, topicId);
  }
}
