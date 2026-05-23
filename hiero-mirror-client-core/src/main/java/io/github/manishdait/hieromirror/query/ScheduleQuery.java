package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.ScheduleInfo;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class ScheduleQuery extends Query<Optional<ScheduleInfo>> {
  private ScheduleId scheduleId;

  public ScheduleQuery() {}

  public ScheduleId getScheduleId() {
    return scheduleId;
  }

  public ScheduleQuery setScheduleId(final @NonNull String scheduleId) {
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");
    return setScheduleId(ScheduleId.fromString(scheduleId));
  }

  public ScheduleQuery setScheduleId(final @NonNull ScheduleId scheduleId) {
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");
    this.scheduleId = scheduleId;
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (scheduleId == null) {
      throw new IllegalStateException("scheduleId must be set before executing query");
    }

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/schedules/" + scheduleId)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<ScheduleInfo> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseScheduleInfo(node);
  }
}
