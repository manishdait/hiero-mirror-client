package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.StakeInfo;
import io.github.manishdait.hieromirror.query.NetworkStakingInfoQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkStakingInfoRequest;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class NetworkStakingInfoRequestImpl implements NetworkStakingInfoRequest {
  private final MirrorNodeClient client;

  public NetworkStakingInfoRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkStakingInfoQuery buildQuery() {
    return new NetworkStakingInfoQuery();
  }

  @Override
  public @NonNull Optional<StakeInfo> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<StakeInfo> call(@NonNull Duration timeout) {
    NetworkStakingInfoQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
