package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.ScheduleInfo;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Optional;
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
  Optional<ScheduleInfo> mapResponse(JsonNode node) {
    return JsonParserImpl.parseScheduleInfo(node);
  }
}
