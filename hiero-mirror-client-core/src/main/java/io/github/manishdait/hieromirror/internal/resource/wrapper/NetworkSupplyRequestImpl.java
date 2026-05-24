package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NetworkSupply;
import io.github.manishdait.hieromirror.query.NetworkSupplyQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkSupplyRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class NetworkSupplyRequestImpl implements NetworkSupplyRequest {
  private final MirrorNodeClient client;

  private List<CriteriaParam<Instant>> timestamp;

  public NetworkSupplyRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkSupplyRequest timestamp(List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull NetworkSupplyQuery getQuery() {
    NetworkSupplyQuery query = new NetworkSupplyQuery();

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    return query;
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
