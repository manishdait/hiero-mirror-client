package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.query.TokenByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.TokenListQuery;
import org.jspecify.annotations.NonNull;

public interface TokenResource {
  @NonNull TokenListQuery findAll();

  default @NonNull TokenByIdQuery findById(String tokenId) {
    return findById(TokenId.fromString(tokenId));
  }

  @NonNull TokenByIdQuery findById(TokenId tokenId);
}
