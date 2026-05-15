package io.github.manishdait.mirrornodeclientj.query;

import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.data.StakeInfo;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class NetworkStakeInfoQuery extends Query<Optional<StakeInfo>> {

  public NetworkStakeInfoQuery(MirrorNodeClient client) {
    super(client);
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/network/stake")
            .method("GET");

    return request.build();
  }

  @Override
  Optional<StakeInfo> mapResponse(JsonNode node) {
    return JsonParserImpl.parseStakeInfo(node);
  }
}
