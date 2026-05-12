package io.github.manishdait.mirrornodeclientj.query;

import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeResponse;
import io.github.manishdait.mirrornodeclientj.internal.core.RestClient;
import java.io.IOException;
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

  public T execute() throws IOException, InterruptedException {
    return execute(Duration.ofSeconds(30));
  }

  public T execute(Duration duration) throws IOException, InterruptedException {
    MirrorNodeResponse response = RestClient.send(buildRequest(), duration);
    return mapResponse(mapper.readTree(response.getBody()));
  }
}
