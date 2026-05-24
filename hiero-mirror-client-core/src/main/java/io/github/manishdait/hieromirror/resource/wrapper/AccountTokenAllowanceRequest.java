package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.TokenAllowance;
import io.github.manishdait.hieromirror.query.AccountTokenAllowanceQuery;
import org.jspecify.annotations.NonNull;

public interface AccountTokenAllowanceRequest
    extends QueryRequest<AccountTokenAllowanceQuery, Page<TokenAllowance>> {
  @NonNull AccountTokenAllowanceRequest limit(int limit);

  @NonNull AccountTokenAllowanceRequest order(Order order);

  @NonNull AccountTokenAllowanceRequest spenderId(CriteriaParam<AccountId> spenderId);

  @NonNull AccountTokenAllowanceRequest tokenId(CriteriaParam<TokenId> tokenId);
}
