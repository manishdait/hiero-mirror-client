package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.CryptoAllowance;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountCryptoAllowanceQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for AccountCryptoAllowanceQuery. */
public interface AccountCryptoAllowanceRequest
    extends QueryRequest<AccountCryptoAllowanceQuery, Page<CryptoAllowance>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull AccountCryptoAllowanceRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull AccountCryptoAllowanceRequest order(Order order);

  /**
   * Sets the spenderId criteria filter.
   *
   * @param spenderId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull AccountCryptoAllowanceRequest spenderId(CriteriaParam<AccountId> spenderId);
}
