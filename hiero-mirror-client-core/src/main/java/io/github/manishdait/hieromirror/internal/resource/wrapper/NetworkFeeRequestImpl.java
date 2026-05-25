package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NetworkFee;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.NetworkFeeQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkFeeRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class NetworkFeeRequestImpl implements NetworkFeeRequest {
  private final MirrorNodeClient client;

  private Order order;
  private List<CriteriaParam<Instant>> timestamp;

  public NetworkFeeRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkFeeRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull NetworkFeeRequest timestamp(List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull NetworkFeeQuery buildQuery() {
    NetworkFeeQuery query = new NetworkFeeQuery();

    if (order != null) {
      query.setOrder(order);
    }

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    return query;
  }

  @Override
  public @NonNull Optional<NetworkFee> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<NetworkFee> call(@NonNull Duration timeout) {
    NetworkFeeQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
