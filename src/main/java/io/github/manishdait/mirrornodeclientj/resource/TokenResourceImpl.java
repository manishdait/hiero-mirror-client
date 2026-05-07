package io.github.manishdait.mirrornodeclientj.resource;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.TokenResource;
import io.github.manishdait.mirrornodeclientj.query.TokenListQuery;
import io.github.manishdait.mirrornodeclientj.query.TokenQuery;
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
  public @NonNull TokenQuery findById(TokenId tokenId) {
    return new TokenQuery(client, tokenId);
  }
}
