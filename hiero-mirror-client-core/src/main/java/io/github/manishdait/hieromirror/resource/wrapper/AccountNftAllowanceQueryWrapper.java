package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NftAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountNftAllowanceQuery;
import org.jspecify.annotations.NonNull;

public interface AccountNftAllowanceQueryWrapper
    extends QueryWrapper<AccountNftAllowanceQuery, Page<NftAllowance>> {
  @NonNull AccountNftAllowanceQueryWrapper limit(int limit);

  @NonNull AccountNftAllowanceQueryWrapper order(Order order);

  @NonNull AccountNftAllowanceQueryWrapper accountId(CriteriaParam<AccountId> spenderId);

  @NonNull AccountNftAllowanceQueryWrapper tokenId(CriteriaParam<TokenId> tokenId);

  @NonNull AccountNftAllowanceQueryWrapper owner(boolean value);
}
