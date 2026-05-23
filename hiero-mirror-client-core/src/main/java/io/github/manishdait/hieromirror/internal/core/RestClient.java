package io.github.manishdait.hieromirror.internal.core;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jspecify.annotations.NonNull;

public class RestClient {
  private static final int MAX_ATTEMPTS = 10;
  private static final long INITIAL_BACKOFF_MS = 200;
  private static final HttpClient client = HttpClient.newHttpClient();

  public static MirrorNodeResponse send(
      @NonNull final MirrorNodeRequest request, Duration duration) {
    Objects.requireNonNull(request, "request must not be null");

    HttpRequest httpRequest =
        HttpRequest.newBuilder().timeout(duration).uri(URI.create(parseUrl(request))).GET().build();

    int attempt = 0;
    while (true) {
      attempt++;
      try {
        HttpResponse<String> httpResponse =
            client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() <= 300) {
          return new MirrorNodeResponse(httpResponse.statusCode(), httpResponse.body());
        }

        if (attempt >= MAX_ATTEMPTS || !shouldRetry(httpResponse.statusCode())) {
          throw new RuntimeException("Fail to query mirror node.");
        }

        performBackoff(attempt);
      } catch (IOException | InterruptedException e) {
        if (attempt >= MAX_ATTEMPTS) {
          throw new RuntimeException("Max attempst reach");
        }

        performBackoff(attempt);
      }
    }
  }

  private static boolean shouldRetry(int statusCode) {
    return statusCode == 408 || statusCode == 429 || (statusCode >= 500 && statusCode <= 599);
  }

  private static void performBackoff(int attempt) {
    long delay = INITIAL_BACKOFF_MS * (long) Math.pow(2, attempt - 1);
    try {
      Thread.sleep(delay);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String parseUrl(MirrorNodeRequest request) {
    if (request.getQueryParams().isEmpty()) {
      return request.getUrl();
    }

    String queryParams =
        request.getQueryParams().entrySet().stream()
            .flatMap(
                entry ->
                    entry.getValue().stream()
                        .map(
                            value ->
                                URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)
                                    + "="
                                    + URLEncoder.encode(value, StandardCharsets.UTF_8)))
            .collect(Collectors.joining("&"));

    return request.getUrl() + "?" + queryParams;
  }
}
