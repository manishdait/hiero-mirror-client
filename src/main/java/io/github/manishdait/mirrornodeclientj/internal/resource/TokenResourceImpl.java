package io.github.manishdait.mirrornodeclientj.internal.resource;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TokenResource;
import io.github.manishdait.mirrornodeclientj.query.GetTokenByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.GetTokenListQuery;
import org.jspecify.annotations.NonNull;

public class TokenResourceImpl implements TokenResource {
  private final MirrorNodeClient client;

  public TokenResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull GetTokenListQuery findAll() {
    return new GetTokenListQuery(client);
  }

  @Override
  public @NonNull GetTokenByIdQuery findById(TokenId tokenId) {
    return new GetTokenByIdQuery(client, tokenId);
  }
}
