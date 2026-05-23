package io.github.manishdait.mirrornodeclientj.core;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.query.AccountByIdQuery;
import io.github.manishdait.hieromirror.query.AccountListQuery;
import org.jspecify.annotations.NonNull;

public interface AccountResource {
  @NonNull AccountListQuery findAll();

  @NonNull AccountByIdQuery findById(AccountId accountId);

  default @NonNull AccountByIdQuery findById(String accountId) {
    return findById(AccountId.fromString(accountId));
  }
}
