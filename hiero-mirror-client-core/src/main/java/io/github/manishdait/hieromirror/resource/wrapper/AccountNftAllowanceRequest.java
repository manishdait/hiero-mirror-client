package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NftAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountNftAllowanceQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for AccountNftAllowanceQuery. */
public interface AccountNftAllowanceRequest
    extends QueryRequest<AccountNftAllowanceQuery, Page<NftAllowance>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull AccountNftAllowanceRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull AccountNftAllowanceRequest order(Order order);

  /**
   * Sets the spenderId criteria filter.
   *
   * @param spenderId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull AccountNftAllowanceRequest spenderId(CriteriaParam<AccountId> spenderId);

  /**
   * Sets the tokenId criteria filter.
   *
   * @param tokenId the {@link CriteriaParam} for tokenId
   * @return {@code this}
   */
  @NonNull AccountNftAllowanceRequest tokenId(CriteriaParam<TokenId> tokenId);

  /**
   * Sets whether accountId specifies the allowance owner (true) or spender (false).
   *
   * @param value true to retrieve allowances granted by the owner
   * @return {@code this}
   */
  @NonNull AccountNftAllowanceRequest owner(boolean value);
}
