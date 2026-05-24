package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NftAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountNftAllowanceQuery;
import org.jspecify.annotations.NonNull;

public interface AccountNftAllowanceRequest
    extends QueryRequest<AccountNftAllowanceQuery, Page<NftAllowance>> {
  @NonNull AccountNftAllowanceRequest limit(int limit);

  @NonNull AccountNftAllowanceRequest order(Order order);

  @NonNull AccountNftAllowanceRequest accountId(CriteriaParam<AccountId> accountId);

  @NonNull AccountNftAllowanceRequest tokenId(CriteriaParam<TokenId> tokenId);

  @NonNull AccountNftAllowanceRequest owner(boolean value);
}
