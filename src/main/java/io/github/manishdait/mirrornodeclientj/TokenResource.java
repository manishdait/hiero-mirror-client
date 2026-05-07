package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.query.GetTokenByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.GetTokenListQuery;
import org.jspecify.annotations.NonNull;

public interface TokenResource {
  @NonNull GetTokenListQuery findAll();

  default @NonNull GetTokenByIdQuery findById(String tokenId) {
    return findById(TokenId.fromString(tokenId));
  }

  @NonNull GetTokenByIdQuery findById(TokenId tokenId);
}
