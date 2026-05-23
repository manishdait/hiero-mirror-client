package io.github.manishdait.mirrornodeclientj.core.test.internal.core;

import io.github.manishdait.hieromirror.internal.core.MirrorNodeResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MirrorNodeResponseTest {
  @Test
  void shouldCreateMirrorNodeResponse() {
    var body = "{\"message\": \"Hello, Hiero!\"}";
    var status = 200;
    var response = new MirrorNodeResponse(status, body);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getBody()).isEqualTo(body);
    Assertions.assertThat(response.getStatus()).isEqualTo(status);
  }
}
