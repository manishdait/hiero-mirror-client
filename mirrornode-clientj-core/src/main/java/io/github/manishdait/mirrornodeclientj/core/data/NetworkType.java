package io.github.manishdait.mirrornodeclientj.core.data;

import java.util.Objects;
import org.jspecify.annotations.NonNull;

public enum NetworkType {
  TESTNET("https://testnet.mirrornode.hedera.com"),
  MAINNET("https://mainnet.mirrornode.hedera.com"),
  PREVIEWNET("https://previewnet.mirrornode.hedera.com"),
  SOLO("https://localhost:50112");

  private final String url;

  NetworkType(String url) {
    this.url = Objects.requireNonNull(url, "url must not be null");
  }

  public @NonNull String getUrl() {
    return this.url;
  }
}
