package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Operator;
import io.github.manishdait.hieromirror.model.TokenInfo;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.time.Instant;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class TokenByIdQuery extends Query<Optional<TokenInfo>> {
  private final TokenId tokenId;

  private CriteriaParam<Instant> timestamp;

  public TokenByIdQuery(MirrorNodeClient client, TokenId tokenId) {
    super(client);
    this.tokenId = tokenId;
  }

  public TokenId getTokenId() {
    return tokenId;
  }

  public CriteriaParam<Instant> getTimestamp() {
    return timestamp;
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
  Optional<TokenInfo> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseTokenInfo(node);
  }
}
