package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.StakeInfo;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
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
  Optional<StakeInfo> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseStakeInfo(node);
  }
}
