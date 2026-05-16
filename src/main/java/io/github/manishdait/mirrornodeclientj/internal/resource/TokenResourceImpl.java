package io.github.manishdait.mirrornodeclientj.internal.resource;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TokenResource;
import io.github.manishdait.mirrornodeclientj.query.TokenByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.TokenListQuery;
import org.jspecify.annotations.NonNull;

public class TokenResourceImpl implements TokenResource {
  private final MirrorNodeClient client;

  public TokenResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull TokenListQuery findAll() {
    return new TokenListQuery(client);
  }

  @Override
  public @NonNull TokenByIdQuery findById(TokenId tokenId) {
    return new TokenByIdQuery(client, tokenId);
  }
}
