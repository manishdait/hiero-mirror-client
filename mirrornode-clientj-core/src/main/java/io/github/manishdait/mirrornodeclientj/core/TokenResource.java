package io.github.manishdait.mirrornodeclientj.core;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.core.query.NftByIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.NftListByAccountIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.NftListByTokenIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.TokenByIdQuery;
import io.github.manishdait.mirrornodeclientj.core.query.TokenListQuery;
import org.jspecify.annotations.NonNull;

public interface TokenResource {
  @NonNull TokenListQuery findAll();

  default @NonNull TokenByIdQuery findById(String tokenId) {
    return findById(TokenId.fromString(tokenId));
  }

  @NonNull TokenByIdQuery findById(TokenId tokenId);

  default @NonNull NftByIdQuery findNftById(String tokenId, long serial) {
    NftId id = new NftId(TokenId.fromString(tokenId), serial);
    return findNftById(id);
  }

  @NonNull NftByIdQuery findNftById(NftId nftId);

  default @NonNull NftListByTokenIdQuery findNftsForTokenId(String tokenId) {
    return findNftsForTokenId(TokenId.fromString(tokenId));
  }

  @NonNull NftListByTokenIdQuery findNftsForTokenId(TokenId tokenId);

  default @NonNull NftListByAccountIdQuery findNftsForAccountId(String accountId) {
    return findNftsForAccountId(AccountId.fromString(accountId));
  }

  @NonNull NftListByAccountIdQuery findNftsForAccountId(AccountId accountId);
}
