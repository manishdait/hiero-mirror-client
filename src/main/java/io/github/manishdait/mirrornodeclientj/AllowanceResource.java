package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.query.CryptoAllowanceQuery;
import io.github.manishdait.mirrornodeclientj.query.TokenAllowanceQuery;
import org.jspecify.annotations.NonNull;

public interface AllowanceResource {
  @NonNull CryptoAllowanceQuery cryptoAllowance(AccountId accountId);

  default @NonNull CryptoAllowanceQuery cryptoAllowance(String accountId) {
    return cryptoAllowance(AccountId.fromString(accountId));
  }

  @NonNull TokenAllowanceQuery tokenAllowance(AccountId accountId);

  default @NonNull TokenAllowanceQuery tokenAllowance(String accountId) {
    return tokenAllowance(AccountId.fromString(accountId));
  }
}
