package io.github.manishdait.hieromirror;

import io.github.manishdait.hieromirror.internal.resource.AccountResourceImpl;
import io.github.manishdait.hieromirror.internal.resource.BlockResourceImpl;
import io.github.manishdait.hieromirror.internal.resource.NetworkResourceImpl;
import io.github.manishdait.hieromirror.resource.AccountResource;
import io.github.manishdait.hieromirror.resource.BlockResource;
import io.github.manishdait.hieromirror.resource.NetworkResource;
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

  /** Access point for executing account related queries. */
  public AccountResource accounts() {
    return new AccountResourceImpl(this);
  }

  /** Access point for executing block related queries. */
  public BlockResource blocks() {
    return new BlockResourceImpl(this);
  }

  /** Access point for executing network related queries. */
  public NetworkResource networks() {
    return new NetworkResourceImpl(this);
  }
}
