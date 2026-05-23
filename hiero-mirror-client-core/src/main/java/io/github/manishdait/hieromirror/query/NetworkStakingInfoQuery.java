package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.StakeInfo;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class NetworkStakingInfoQuery extends Query<Optional<StakeInfo>> {

  public NetworkStakingInfoQuery() {}

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/network/stake")
            .method("GET");

    return request.build();
  }

  @Override
  Optional<StakeInfo> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseStakeInfo(node);
  }
}
