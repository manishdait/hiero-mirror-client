package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountNftListQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for AccountNftListQuery. */
public interface AccountNftListRequest extends QueryRequest<AccountNftListQuery, Page<Nft>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull AccountNftListRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull AccountNftListRequest order(Order order);

  /**
   * Sets the spenderId criteria filter.
   *
   * @param spenderId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull AccountNftListRequest senderId(CriteriaParam<AccountId> spenderId);

  /**
   * Sets the tokenId criteria filter.
   *
   * @param tokenId the {@link CriteriaParam} for tokenId
   * @return {@code this}
   */
  @NonNull AccountNftListRequest tokenId(CriteriaParam<TokenId> tokenId);

  /**
   * Sets the serialNumber criteria filter.
   *
   * @param serialNumber the {@link CriteriaParam} for serial number
   * @return {@code this}
   */
  @NonNull AccountNftListRequest serialNumber(CriteriaParam<Long> serialNumber);
}
