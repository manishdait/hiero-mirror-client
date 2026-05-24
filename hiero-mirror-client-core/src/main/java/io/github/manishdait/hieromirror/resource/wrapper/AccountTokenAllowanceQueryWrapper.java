package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.TokenAllowance;
import io.github.manishdait.hieromirror.query.AccountTokenAllowanceQuery;
import org.jspecify.annotations.NonNull;

public interface AccountTokenAllowanceQueryWrapper
    extends QueryWrapper<AccountTokenAllowanceQuery, Page<TokenAllowance>> {
  @NonNull AccountTokenAllowanceQueryWrapper limit(int limit);

  @NonNull AccountTokenAllowanceQueryWrapper order(Order order);

  @NonNull AccountTokenAllowanceQueryWrapper spenderId(CriteriaParam<AccountId> spenderId);

  @NonNull AccountTokenAllowanceQueryWrapper tokenId(CriteriaParam<TokenId> tokenId);
}
