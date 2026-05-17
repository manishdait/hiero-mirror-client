package io.github.manishdait.mirrornodeclientj.core.internal.core;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MirrorNodeRequestTest {
  private final String TEST_URL = "http://test-mirrornode";
  private final String TEST_METHOD = "GET";
  private final String TEST_QUERY_PARAM_KEY = "key";
  private final String TEST_QUERY_PARAM_VALUE = "value";

  @Test
  void shouldCreateMirrorNodeRequest() {
    var request =
        MirrorNodeRequest.newBuilder()
            .url(TEST_URL)
            .method(TEST_METHOD)
            .queryParam(TEST_QUERY_PARAM_KEY, TEST_QUERY_PARAM_VALUE)
            .build();

    Assertions.assertThat(request.getUrl()).isEqualTo(TEST_URL);
    Assertions.assertThat(request.getMethod()).isEqualTo(TEST_METHOD);
    Assertions.assertThat(request.getQueryParams())
        .containsEntry(TEST_QUERY_PARAM_KEY, List.of(TEST_QUERY_PARAM_VALUE));
  }

  @Test
  void shouldAddQueryParamToParamsList() {
    var param1 = "value1";
    var param2 = "value2";

    var request =
        MirrorNodeRequest.newBuilder()
            .url(TEST_URL)
            .method(TEST_METHOD)
            .queryParam(TEST_QUERY_PARAM_KEY, param1)
            .queryParam(TEST_QUERY_PARAM_KEY, param2)
            .build();

    Assertions.assertThat(request.getUrl()).isEqualTo(TEST_URL);
    Assertions.assertThat(request.getMethod()).isEqualTo(TEST_METHOD);
    Assertions.assertThat(request.getQueryParams())
        .containsEntry(TEST_QUERY_PARAM_KEY, List.of(param1, param2));
  }

  @Test
  void shouldCreateMirrorNodeRequestUsingParamsList() {
    var request =
        MirrorNodeRequest.newBuilder()
            .url(TEST_URL)
            .method(TEST_METHOD)
            .queryParams(TEST_QUERY_PARAM_KEY, List.of(TEST_QUERY_PARAM_VALUE))
            .build();

    Assertions.assertThat(request.getUrl()).isEqualTo(TEST_URL);
    Assertions.assertThat(request.getMethod()).isEqualTo(TEST_METHOD);
    Assertions.assertThat(request.getQueryParams())
        .containsEntry(TEST_QUERY_PARAM_KEY, List.of(TEST_QUERY_PARAM_VALUE));
  }

  @Test
  void shouldOverrideQueryParamWhenUsingParamsList() {
    var request =
        MirrorNodeRequest.newBuilder()
            .url(TEST_URL)
            .method(TEST_METHOD)
            .queryParams(TEST_QUERY_PARAM_KEY, List.of(TEST_QUERY_PARAM_VALUE))
            .build();

    Assertions.assertThat(request.getUrl()).isEqualTo(TEST_URL);
    Assertions.assertThat(request.getMethod()).isEqualTo(TEST_METHOD);
    Assertions.assertThat(request.getQueryParams())
        .containsEntry(TEST_QUERY_PARAM_KEY, List.of(TEST_QUERY_PARAM_VALUE));
  }

  @Test
  void shouldDefaultToGetWhenMethodProvided() {
    var request =
        MirrorNodeRequest.newBuilder()
            .url(TEST_URL)
            .queryParam(TEST_QUERY_PARAM_KEY, TEST_QUERY_PARAM_VALUE)
            .build();

    Assertions.assertThat(request.getUrl()).isEqualTo(TEST_URL);
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");
    Assertions.assertThat(request.getQueryParams())
        .containsEntry(TEST_QUERY_PARAM_KEY, List.of(TEST_QUERY_PARAM_VALUE));
  }
}
