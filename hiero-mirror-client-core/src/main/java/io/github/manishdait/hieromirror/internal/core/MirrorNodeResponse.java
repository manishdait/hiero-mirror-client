package io.github.manishdait.hieromirror.internal.core;

import org.jspecify.annotations.Nullable;

/** Class that represent the response form MirrorNode RestAPI. */
public class MirrorNodeResponse {
  private final int status;
  @Nullable private final String body;

  /** Constructor. */
  public MirrorNodeResponse(final int status, @Nullable final String body) {
    this.status = status;
    this.body = body;
  }

  /**
   * Returns the status code for the response.
   *
   * @return the status code
   */
  public int getStatus() {
    return status;
  }

  /**
   * Returns the body for the api response.
   *
   * @return the response body
   */
  @Nullable public String getBody() {
    return body;
  }
}
