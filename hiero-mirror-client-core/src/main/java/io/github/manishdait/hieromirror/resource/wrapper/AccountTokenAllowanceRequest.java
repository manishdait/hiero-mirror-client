package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.TokenAllowance;
import io.github.manishdait.hieromirror.query.AccountTokenAllowanceQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for AccountTokenAllowanceQuery. */
public interface AccountTokenAllowanceRequest
    extends QueryRequest<AccountTokenAllowanceQuery, Page<TokenAllowance>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull AccountTokenAllowanceRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull AccountTokenAllowanceRequest order(Order order);

  /**
   * Sets the spenderId criteria filter.
   *
   * @param spenderId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull AccountTokenAllowanceRequest spenderId(CriteriaParam<AccountId> spenderId);

  /**
   * Sets the tokenId criteria filter.
   *
   * @param tokenId the {@link CriteriaParam} for tokenId
   * @return {@code this}
   */
  @NonNull AccountTokenAllowanceRequest tokenId(CriteriaParam<TokenId> tokenId);
}
