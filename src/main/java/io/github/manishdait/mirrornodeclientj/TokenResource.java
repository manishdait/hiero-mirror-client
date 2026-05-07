package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.query.TokenListQuery;
import io.github.manishdait.mirrornodeclientj.query.TokenQuery;
import org.jspecify.annotations.NonNull;

public interface TokenResource {
  @NonNull TokenListQuery findAll();

  default @NonNull TokenQuery findById(String tokenId) {
    return findById(TokenId.fromString(tokenId));
  }

  @NonNull TokenQuery findById(TokenId tokenId);
}
