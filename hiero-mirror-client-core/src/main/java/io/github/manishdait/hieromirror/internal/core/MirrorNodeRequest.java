package io.github.manishdait.hieromirror.internal.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

/** Class that represent the MirrorNode API request. */
public class MirrorNodeRequest {
  private final String url;
  private final String method;
  private final Map<String, List<String>> queryParams;

  /** Constructor. */
  private MirrorNodeRequest(@NonNull final Builder builder) {
    Objects.requireNonNull(builder, "builder must not be null");

    this.url = builder.url;
    this.method = builder.method;
    this.queryParams = Collections.unmodifiableMap(builder.queryParams);
  }

  /**
   * Return new MirrorNodeRequest builder.
   *
   * @return request builder
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns RestAPI url endpoint.
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns the HTTP method used.
   *
   * @return the http method
   */
  public String getMethod() {
    return method;
  }

  /**
   * Returns the query params for the API request.
   *
   * @return map of query param
   */
  public Map<String, List<String>> getQueryParams() {
    return queryParams;
  }

  /** Builder class for MirrorNodeRequest. */
  public static class Builder {
    private String url;
    private String method = "GET";
    private final Map<String, List<String>> queryParams = new HashMap<>();

    public Builder() {}

    /**
     * Set the url for the request.
     *
     * @param url the url
     * @return {@code this}
     */
    public Builder url(@NonNull final String url) {
      Objects.requireNonNull(url, "url must not be null");
      this.url = url;
      return this;
    }

    /**
     * Set the http method for request. Default {@code GET}
     *
     * @param method http method to use
     * @return {@code this}
     */
    public Builder method(@NonNull final String method) {
      Objects.requireNonNull(method, "method must not be null");
      this.method = method;
      return this;
    }

    /**
     * Add query params to for the request.
     *
     * @param key name of query param
     * @param value value for query param
     * @return {@code this}
     */
    public Builder queryParam(@NonNull final String key, @NonNull final String value) {
      Objects.requireNonNull(key, "key must not be null");
      Objects.requireNonNull(value, "value must not be null");

      queryParams.computeIfAbsent(key, k -> new ArrayList<String>()).add(value);
      return this;
    }

    /**
     * Set query params for the request.
     *
     * @param key name of query param
     * @param values list of values for query param
     * @return {@code this}
     */
    public Builder queryParams(@NonNull final String key, @NonNull final List<String> values) {
      Objects.requireNonNull(key, "key must not be null");
      Objects.requireNonNull(values, "values must not be null");

      queryParams.put(key, values);
      return this;
    }

    /**
     * Create new instance of MirrorNodeRequest from builder.
     *
     * @return new instance of {@link MirrorNodeRequest}
     */
    @NonNull public MirrorNodeRequest build() {
      return new MirrorNodeRequest(this);
    }
  }
}
