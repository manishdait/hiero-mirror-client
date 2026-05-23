package io.github.manishdait.hieromirror;

import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class MirrorNodeClient {
  private final String baseUrl;
  private Duration timeout = Duration.ofSeconds(30);

  public MirrorNodeClient(final @NonNull HieroNetwork hieroNetwork) {
    Objects.requireNonNull(hieroNetwork, "hieroNetwork must not be null");
    this(hieroNetwork.getUrl());
  }

  public MirrorNodeClient(final @NonNull String baseUrl) {
    this.baseUrl = Objects.requireNonNull(baseUrl, "baseUrl must not be null");
    ;
  }

  public @NonNull String getBaseUrl() {
    return this.baseUrl;
  }

  public @NonNull Duration getTimeout() {
    return timeout;
  }

  public void setTimeout(final @NonNull Duration timeout) {
    this.timeout = Objects.requireNonNull(timeout, "timeout must not be null");
  }
}
