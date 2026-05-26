package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.TokenInfo;
import io.github.manishdait.hieromirror.query.TokenQuery;
import io.github.manishdait.hieromirror.resource.wrapper.TokenRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class TokenRequestImpl implements TokenRequest {
  private final MirrorNodeClient client;
  private final TokenId tokenId;

  private CriteriaParam<Instant> timestamp;

  public TokenRequestImpl(final @NonNull MirrorNodeClient client, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.client = client;
    this.tokenId = tokenId;
  }

  @Override
  public @NonNull TokenRequest timestamp(CriteriaParam<Instant> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull TokenQuery buildQuery() {
    TokenQuery query = new TokenQuery().setTokenId(tokenId);

    if (timestamp != null) {
      query.setTimestamp(timestamp.getOperator(), timestamp.getValue());
    }

    return query;
  }

  @Override
  public @NonNull Optional<TokenInfo> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<TokenInfo> call(@NonNull Duration timeout) {
    TokenQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
