package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountListQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for AccountListQuery. */
public interface AccountListRequest extends QueryRequest<AccountListQuery, Page<AccountInfo>> {
  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull AccountListRequest order(Order order);

  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull AccountListRequest limit(int limit);

  /**
   * Set whether to include balance fields.
   *
   * @param value {@code true} to include balances, {@code false} to omit
   * @return {@code this}
   */
  @NonNull AccountListRequest includeBalance(boolean value);

  /**
   * Sets the account public key criteria filter.
   *
   * @param publicKey the {@link PublicKey} instance
   * @return {@code this}
   */
  @NonNull AccountListRequest publicKey(PublicKey publicKey);

  /**
   * Sets an accountId criteria filter.
   *
   * @param accountId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull AccountListRequest accountId(CriteriaParam<AccountId> accountId);

  /**
   * Sets a balance criteria filter.
   *
   * @param balance the {@link CriteriaParam} for account balance
   * @return {@code this}
   */
  @NonNull AccountListRequest balance(CriteriaParam<Hbar> balance);
}
