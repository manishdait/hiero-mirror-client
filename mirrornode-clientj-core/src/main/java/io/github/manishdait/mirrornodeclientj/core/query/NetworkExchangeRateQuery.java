package io.github.manishdait.mirrornodeclientj.core.query;

import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.core.data.ExchangeRate;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class NetworkExchangeRateQuery extends Query<Optional<ExchangeRate>> {
  private final List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public NetworkExchangeRateQuery(MirrorNodeClient client) {
    super(client);
  }

  public NetworkExchangeRateQuery timestamp(
      final @NonNull Operator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");

    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder().url(client.getBaseUrl() + "/api/v1/network/exchangerate");

    for (CriteriaParam<Instant> timestamp : timestamps) {
      request.queryParam(
          "timestamp",
          timestamp.getOperator().getValue()
              + ":"
              + timestamp.getValue().getEpochSecond()
              + "."
              + timestamp.getValue().getNano());
    }

    return request.build();
  }

  @Override
  Optional<ExchangeRate> mapResponse(JsonNode node) {
    return JsonParserImpl.parseExchangeRate(node);
  }
}
