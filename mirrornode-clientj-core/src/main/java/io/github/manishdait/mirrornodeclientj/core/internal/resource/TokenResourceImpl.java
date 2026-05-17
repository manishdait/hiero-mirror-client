package io.github.manishdait.mirrornodeclientj.core.internal.resource;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.TokenResource;
import io.github.manishdait.mirrornodeclientj.core.query.NftByIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.NftListByAccountIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.NftListByTokenIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.TokenByIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.TokenListQuery;
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

  @Override
  public @NonNull NftByIdQuery findNftById(NftId nftId) {
    return new NftByIdQuery(client, nftId);
  }

  @Override
  public @NonNull NftListByTokenIdQuery findNftsForTokenId(TokenId tokenId) {
    return new NftListByTokenIdQuery(client, tokenId);
  }

  @Override
  public @NonNull NftListByAccountIdQuery findNftsForAccountId(AccountId accountId) {
    return new NftListByAccountIdQuery(client, accountId);
  }
}
