package io.github.manishdait.mirrornodeclientj.query;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.internal.parser.JsonParserImpl;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.Operator;
import io.github.manishdait.mirrornodeclientj.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.data.TokenInfo;
import java.time.Instant;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class GetTokenByIdQuery extends Query<Optional<TokenInfo>> {
  private final TokenId tokenId;

  private CriteriaParam<Instant> timestamp;

  public GetTokenByIdQuery(MirrorNodeClient client, TokenId tokenId) {
    super(client);
    this.tokenId = tokenId;
  }

  public GetTokenByIdQuery timestamp(Operator operator, Instant timestamp) {
    this.timestamp = new CriteriaParam<>(operator, timestamp);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/tokens/" + tokenId)
            .method("GET");

    if (timestamp != null) {
      request.queryParam(
          "timestamp",
          timestamp.getOperator().getValue()
              + ":"
              + timestamp.getValue().getEpochSecond()
              + "."
              + timestamp.getValue().getNano());
    }

    return request.build();
  }

  @Override
  Optional<TokenInfo> mapResponse(JsonNode node) {
    return JsonParserImpl.parseTokenInfo(node);
  }
}
