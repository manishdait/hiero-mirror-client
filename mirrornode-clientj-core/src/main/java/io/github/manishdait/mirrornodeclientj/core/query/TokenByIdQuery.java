package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.CriteriaParam;
import io.github.manishdait.mirrornodeclientj.core.data.Operator;
import io.github.manishdait.mirrornodeclientj.core.data.TokenInfo;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.time.Instant;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class TokenByIdQuery extends Query<Optional<TokenInfo>> {
  private final TokenId tokenId;

  private CriteriaParam<Instant> timestamp;

  public TokenByIdQuery(MirrorNodeClient client, TokenId tokenId) {
    super(client);
    this.tokenId = tokenId;
  }

  public TokenByIdQuery timestamp(Operator operator, Instant timestamp) {
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
