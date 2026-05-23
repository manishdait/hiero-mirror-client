package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeResponse;
import io.github.manishdait.hieromirror.internal.core.RestClient;
import java.time.Duration;
import java.util.Objects;

import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Abstract base class for all Hiero Mirror Node API queries.
 */
public abstract class Query<T> {
  static final ObjectMapper mapper = new ObjectMapper();

  /**
   * Constructs the specific {@link MirrorNodeRequest} required to execute this query.
   *
   * @param client the mirror node client
   * @return mirror node request object
   */
  abstract MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client);

  /**
   * Map the JSON response to the specific Response class.
   *
   * @param node the root JSON node of api response
   * @return response class object
   */
  abstract T mapResponse(final @NonNull JsonNode node);

  /**
   * Execute mirror node query using the default timeout.
   *
   * @param client the mirror node client
   * @return response class object
   */
  public T execute(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    return execute(client, client.getTimeout());
  }

  /**
   * Execute mirror node query using the custom timeout.
   *
   * @param client the mirror node client
   * @param timeout the maximum duration to wait for the network response
   * @return response class object
   */
  public T execute(final @NonNull MirrorNodeClient client, final @NonNull Duration timeout) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(timeout, "timeout must not be null");

    MirrorNodeResponse response = RestClient.send(buildRequest(client), timeout);
    return mapResponse(mapper.readTree(response.getBody()));
  }
}
