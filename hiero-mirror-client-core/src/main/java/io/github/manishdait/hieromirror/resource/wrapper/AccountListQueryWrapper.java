package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import org.jspecify.annotations.NonNull;

/** Wrapper for AccountListQuery. */
public interface AccountListQueryWrapper {
  @NonNull AccountListQueryWrapper order(Order order);

  @NonNull AccountListQueryWrapper limit(int limit);

  @NonNull AccountListQueryWrapper includeBalance(boolean includeBalance);

  @NonNull AccountListQueryWrapper publicKey(PublicKey publicKey);

  @NonNull AccountListQueryWrapper accountId(CriteriaParam<AccountId> accountId);

  @NonNull AccountListQueryWrapper balance(CriteriaParam<Hbar> balance);

  @NonNull Page<AccountInfo> call();
}
