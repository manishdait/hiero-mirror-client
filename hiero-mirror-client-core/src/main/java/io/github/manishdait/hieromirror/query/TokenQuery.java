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
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;

/** Query to get token by id. */
public class TokenQuery extends Query<Optional<TokenInfo>> {
  private TokenId tokenId;

  @Nullable private CriteriaParam<Instant> timestamp;

  /** Constructor. */
  public TokenQuery() {}

  /**
   * Gets the tokenId of token to fetch.
   *
   * @return the tokenId
   */
  public TokenId getTokenId() {
    return tokenId;
  }

  /**
   * Sets the tokenId of token to fetch.
   *
   * @param tokenId the string representation of tokenId
   * @return {@code this}
   */
  public TokenQuery setTokenId(final @NonNull String tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return setTokenId(TokenId.fromString(tokenId));
  }

  /**
   * Sets the tokenId of token to fetch.
   *
   * @param tokenId the target {@link TokenId} instance
   * @return {@code this}
   */
  public TokenQuery setTokenId(final @NonNull TokenId tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    this.tokenId = tokenId;
    return this;
  }

  /**
   * Gets timestamp criteria filter.
   *
   * @return the timestamp criteria
   */
  public @Nullable CriteriaParam<Instant> getTimestamp() {
    return timestamp;
  }

  /**
   * Sets timestamp criteria filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param timestamp the timestamp criteria
   * @return {@code this}
   */
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
