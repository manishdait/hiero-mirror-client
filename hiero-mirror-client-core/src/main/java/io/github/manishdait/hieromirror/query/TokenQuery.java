package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.model.TokenInfo;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class TokenQuery extends Query<Optional<TokenInfo>> {
  private TokenId tokenId;

  private CriteriaParam<Instant> timestamp;

  public TokenQuery() {}

  public TokenId getTokenId() {
    return tokenId;
  }

  public TokenQuery setTokenId(final @NonNull String tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return setTokenId(TokenId.fromString(tokenId));
  }

  public TokenQuery setTokenId(final @NonNull TokenId tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    this.tokenId = tokenId;
    return this;
  }

  public CriteriaParam<Instant> getTimestamp() {
    return timestamp;
  }

  public TokenQuery setTimestamp(
      final @NonNull QueryOperator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamp = new CriteriaParam<>(operator, timestamp);
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (tokenId == null) {
      throw new IllegalStateException("tokenId must set before executing query");
    }

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/tokens/" + tokenId)
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
    return MirrorNodeJsonParser.parseTokenInfo(node);
  }
}
