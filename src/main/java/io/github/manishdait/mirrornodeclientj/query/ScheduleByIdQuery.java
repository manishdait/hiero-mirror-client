package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.ScheduleInfo;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class ScheduleByIdQuery extends Query<Optional<ScheduleInfo>> {
  private final ScheduleId scheduleId;

  public ScheduleByIdQuery(MirrorNodeClient client, ScheduleId scheduleId) {
    super(client);
    this.scheduleId = scheduleId;
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
