package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ScheduleQueryTest {
  @Test
  void shouldCreateQueryWithDefaults() {
    var query = new ScheduleQuery();

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getScheduleId()).isNull();
  }

  @Test
  void shouldSetScheduleId() {
    var id = new ScheduleId(0, 0, 1);
    var query = new ScheduleQuery().setScheduleId(id);

    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getScheduleId()).isEqualTo(id);
  }

  @Test
  void shouldBuildRequestWithAllParams() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var id = new ScheduleId(0, 0, 1);
    var query = new ScheduleQuery().setScheduleId(id);

    var request = query.buildRequest(mockClient);

    Assertions.assertThat(request).isNotNull();
    Assertions.assertThat(request.getUrl())
        .isEqualTo("https://example.com/api/v1/schedules/" + id.toString());
    Assertions.assertThat(request.getMethod()).isEqualTo("GET");

    Assertions.assertThat(request.getQueryParams()).isEmpty();
  }

  @Test
  void shouldRaiseErrorOnBuildRequestIfNoIdentifierSet() {
    var mockClient = Mockito.mock(MirrorNodeClient.class);
    Mockito.when(mockClient.getBaseUrl()).thenReturn("https://example.com");

    var query = new ScheduleQuery();

    Assertions.assertThatThrownBy(() -> query.buildRequest(mockClient))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("scheduleId must be set before executing query");
  }
}
