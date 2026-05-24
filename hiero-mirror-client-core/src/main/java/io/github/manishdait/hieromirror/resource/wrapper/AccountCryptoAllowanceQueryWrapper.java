package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.CryptoAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountCryptoAllowanceQuery;
import org.jspecify.annotations.NonNull;

public interface AccountCryptoAllowanceQueryWrapper
    extends QueryWrapper<AccountCryptoAllowanceQuery, Page<CryptoAllowance>> {
  @NonNull AccountCryptoAllowanceQueryWrapper limit(int limit);

  @NonNull AccountCryptoAllowanceQueryWrapper order(Order order);

  @NonNull AccountCryptoAllowanceQueryWrapper spenderId(CriteriaParam<AccountId> spenderId);
}
