package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.query.GetAccountByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.GetAccountListQuery;
import org.jspecify.annotations.NonNull;

public interface AccountResource {
  @NonNull GetAccountListQuery findAll();

  @NonNull GetAccountByIdQuery findById(AccountId accountId);

  default @NonNull GetAccountByIdQuery findById(String accountId) {
    return findById(AccountId.fromString(accountId));
  }
}
