package io.github.manishdait.hieromirror;

import org.jspecify.annotations.NonNull;

public enum HieroNetwork {
  TESTNET("https://testnet.mirrornode.hedera.com"),
  MAINNET("https://mainnet.mirrornode.hedera.com"),
  PREVIEWNET("https://previewnet.mirrornode.hedera.com"),
  SOLO("http://localhost:5551");

  private final String url;

  HieroNetwork(String url) {
    this.url = url;
  }

  public @NonNull String getUrl() {
    return this.url;
  }
}
