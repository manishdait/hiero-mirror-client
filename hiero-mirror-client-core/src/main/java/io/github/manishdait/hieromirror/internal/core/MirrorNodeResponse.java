package io.github.manishdait.hieromirror.internal.core;

import org.jspecify.annotations.Nullable;

public class MirrorNodeResponse {
  private final int status;
  private final String body;

  public MirrorNodeResponse(final int status, @Nullable final String body) {
    this.status = status;
    this.body = body;
  }

  public int getStatus() {
    return status;
  }

  @Nullable public String getBody() {
    return body;
  }
}
