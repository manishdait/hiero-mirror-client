package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.ScheduleInfo;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class ScheduleByIdQuery extends Query<Optional<ScheduleInfo>> {
  private final ScheduleId scheduleId;

  public ScheduleByIdQuery(MirrorNodeClient client, ScheduleId scheduleId) {
    super(client);
    this.scheduleId = scheduleId;
  }

  public ScheduleId getScheduleId() {
    return scheduleId;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/schedules/" + scheduleId)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<ScheduleInfo> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseScheduleInfo(node);
  }
}
