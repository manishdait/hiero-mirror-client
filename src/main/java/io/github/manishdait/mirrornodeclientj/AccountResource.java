package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.query.AccountListQuery;
import io.github.manishdait.mirrornodeclientj.query.AccountQuery;
import org.jspecify.annotations.NonNull;

public interface AccountResource {
  @NonNull AccountListQuery findAll();

  @NonNull AccountQuery findById(AccountId accountId);

  default @NonNull AccountQuery findById(String accountId) {
    return findById(AccountId.fromString(accountId));
  }
}
