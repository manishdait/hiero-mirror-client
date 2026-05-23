package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;

public interface AccountListQueryWrapper {
  AccountListQueryWrapper order(Order order);

  AccountListQueryWrapper limit(int limit);

  AccountListQueryWrapper includeBalance(boolean includeBalance);

  AccountListQueryWrapper publicKey(PublicKey publicKey);

  AccountListQueryWrapper accountId(CriteriaParam<AccountId> accountId);

  AccountListQueryWrapper balance(CriteriaParam<Hbar> balance);

  Page<AccountInfo> call();
}
