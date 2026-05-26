package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.TokenNftListQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for TokenNftListQuery. */
public interface TokenNftListRequest extends QueryRequest<TokenNftListQuery, Page<Nft>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull TokenNftListRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull TokenNftListRequest order(Order order);

  /**
   * Sets the accountId criteria param.
   *
   * @param accountId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull TokenNftListRequest accountId(CriteriaParam<AccountId> accountId);

  /**
   * Sets the serialNumber criteria filter.
   *
   * @param serialNumber the {@link CriteriaParam} for serial number
   * @return {@code this}
   */
  @NonNull TokenNftListRequest serialNumber(CriteriaParam<Long> serialNumber);
}
