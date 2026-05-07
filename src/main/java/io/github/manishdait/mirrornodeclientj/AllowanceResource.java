package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.query.CryptoAllowanceQuery;
import io.github.manishdait.mirrornodeclientj.query.NftAllowanceQuery;
import io.github.manishdait.mirrornodeclientj.query.TokenAllowanceQuery;
import org.jspecify.annotations.NonNull;

public interface AllowanceResource {
  @NonNull CryptoAllowanceQuery getCryptoAllowance(AccountId accountId);

  default @NonNull CryptoAllowanceQuery getCryptoAllowance(String accountId) {
    return getCryptoAllowance(AccountId.fromString(accountId));
  }

  @NonNull TokenAllowanceQuery getTokenAllowance(AccountId accountId);

  default @NonNull TokenAllowanceQuery getTokenAllowance(String accountId) {
    return getTokenAllowance(AccountId.fromString(accountId));
  }

  @NonNull NftAllowanceQuery getNftAllowance(AccountId accountId);

  default @NonNull NftAllowanceQuery getNftAllowance(String accountId) {
    return getNftAllowance(AccountId.fromString(accountId));
  }
}
