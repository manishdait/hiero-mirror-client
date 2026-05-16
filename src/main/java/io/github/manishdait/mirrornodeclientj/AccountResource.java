package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.query.AccountByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.AccountListQuery;
import org.jspecify.annotations.NonNull;

public interface AccountResource {
  @NonNull AccountListQuery findAll();

  @NonNull AccountByIdQuery findById(AccountId accountId);

  default @NonNull AccountByIdQuery findById(String accountId) {
    return findById(AccountId.fromString(accountId));
  }
}
