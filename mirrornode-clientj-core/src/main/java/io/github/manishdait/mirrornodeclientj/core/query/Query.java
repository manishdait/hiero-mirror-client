package io.github.manishdait.mirrornodeclientj.core.query;

import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeResponse;
import io.github.manishdait.mirrornodeclientj.core.internal.core.RestClient;
import java.time.Duration;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public abstract class Query<T> {
  static final ObjectMapper mapper = new ObjectMapper();
  final MirrorNodeClient client;

  public Query(MirrorNodeClient client) {
    this.client = client;
  }

  abstract MirrorNodeRequest buildRequest();

  abstract T mapResponse(JsonNode node);

  public T execute() {
    return execute(Duration.ofSeconds(30));
  }

  public T execute(Duration duration) {
    MirrorNodeResponse response = RestClient.send(buildRequest(), duration);
    return mapResponse(mapper.readTree(response.getBody()));
  }
}
