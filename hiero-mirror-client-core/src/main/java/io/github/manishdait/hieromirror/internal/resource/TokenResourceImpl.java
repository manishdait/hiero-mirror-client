package io.github.manishdait.hieromirror.internal.resource;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.TokenResource;
import io.github.manishdait.hieromirror.query.NftByIdQuery;
import io.github.manishdait.hieromirror.query.NftListByAccountIdQuery;
import io.github.manishdait.hieromirror.query.NftListByTokenIdQuery;
import io.github.manishdait.hieromirror.query.NftTransactionHistoryQuery;
import io.github.manishdait.hieromirror.query.TokenByIdQuery;
import io.github.manishdait.hieromirror.query.TokenListQuery;
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

  @Override
  public @NonNull NftTransactionHistoryQuery findNftTransactions(NftId nftId) {
    return new NftTransactionHistoryQuery(client, nftId);
  }
}
