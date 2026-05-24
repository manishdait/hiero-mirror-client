package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.NetworkSupply;
import io.github.manishdait.hieromirror.query.NetworkSupplyQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkSupplyRequest;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class NetworkSupplyRequestImpl implements NetworkSupplyRequest {
  private final MirrorNodeClient client;

  public NetworkSupplyRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkSupplyQuery getQuery() {
    return new NetworkSupplyQuery();
  }

  @Override
  public @NonNull Optional<NetworkSupply> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<NetworkSupply> call(@NonNull Duration timeout) {
    NetworkSupplyQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
