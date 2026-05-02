package io.github.manishdait.mirrornodeclientj.core;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jspecify.annotations.NonNull;

public class RestClient {
  private static final HttpClient client = HttpClient.newHttpClient();

  public static MirrorNodeResponse send(@NonNull final MirrorNodeRequest request)
      throws IOException, InterruptedException {
    Objects.requireNonNull(request, "request must not be null");
    HttpRequest httpRequest =
        HttpRequest.newBuilder().uri(URI.create(parseUrl(request))).GET().build();

    HttpResponse<String> httpResponse =
        client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    System.out.println(parseUrl(request));

    return new MirrorNodeResponse(httpResponse.statusCode(), httpResponse.body());
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
