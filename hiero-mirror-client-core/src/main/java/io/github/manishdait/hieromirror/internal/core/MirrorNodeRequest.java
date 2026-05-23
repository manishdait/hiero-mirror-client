package io.github.manishdait.hieromirror.internal.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class MirrorNodeRequest {
  private final String url;
  private final String method;
  private final Map<String, List<String>> queryParams;

  private MirrorNodeRequest(@NonNull final Builder builder) {
    Objects.requireNonNull(builder, "builder must not be null");

    this.url = builder.url;
    this.method = builder.method;
    this.queryParams = Collections.unmodifiableMap(builder.queryParams);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getUrl() {
    return url;
  }

  public String getMethod() {
    return method;
  }

  public Map<String, List<String>> getQueryParams() {
    return queryParams;
  }

  public static class Builder {
    private String url;
    private String method = "GET";
    private final Map<String, List<String>> queryParams = new HashMap<>();

    public Builder() {}

    public Builder url(@NonNull final String url) {
      Objects.requireNonNull(url, "url must not be null");
      this.url = url;
      return this;
    }

    public Builder method(@NonNull final String method) {
      Objects.requireNonNull(method, "method must not be null");
      this.method = method;
      return this;
    }

    public Builder queryParam(@NonNull final String key, @NonNull final String value) {
      Objects.requireNonNull(key, "key must not be null");
      Objects.requireNonNull(value, "value must not be null");

      queryParams.computeIfAbsent(key, k -> new ArrayList<String>()).add(value);
      return this;
    }

    public Builder queryParams(@NonNull final String key, @NonNull final List<String> values) {
      Objects.requireNonNull(key, "key must not be null");
      Objects.requireNonNull(values, "values must not be null");

      queryParams.put(key, values);
      return this;
    }

    @NonNull public MirrorNodeRequest build() {
      return new MirrorNodeRequest(this);
    }
  }
}
