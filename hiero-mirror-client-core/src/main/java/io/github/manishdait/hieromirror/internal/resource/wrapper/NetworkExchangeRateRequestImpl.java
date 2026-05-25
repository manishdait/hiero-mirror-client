package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.ExchangeRate;
import io.github.manishdait.hieromirror.query.NetworkExchangeRateQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkExchangeRateRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class NetworkExchangeRateRequestImpl implements NetworkExchangeRateRequest {
  private final MirrorNodeClient client;

  private List<CriteriaParam<Instant>> timestamp;

  public NetworkExchangeRateRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkExchangeRateRequest timestamp(List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull NetworkExchangeRateQuery buildQuery() {
    NetworkExchangeRateQuery query = new NetworkExchangeRateQuery();

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    return query;
  }

  @Override
  public @NonNull Optional<ExchangeRate> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<ExchangeRate> call(@NonNull Duration timeout) {
    NetworkExchangeRateQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
