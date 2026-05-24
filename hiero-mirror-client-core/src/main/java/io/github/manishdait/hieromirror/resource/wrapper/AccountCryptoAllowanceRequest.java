package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.CryptoAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountCryptoAllowanceQuery;
import org.jspecify.annotations.NonNull;

public interface AccountCryptoAllowanceRequest
    extends QueryRequest<AccountCryptoAllowanceQuery, Page<CryptoAllowance>> {
  @NonNull AccountCryptoAllowanceRequest limit(int limit);

  @NonNull AccountCryptoAllowanceRequest order(Order order);

  @NonNull AccountCryptoAllowanceRequest spenderId(CriteriaParam<AccountId> spenderId);
}
