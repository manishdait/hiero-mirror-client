package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.AccountQuery;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for AccountQuery. */
public interface AccountRequest extends QueryRequest<AccountQuery, Optional<AccountInfo>> {
  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull AccountRequest order(Order order);

  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull AccountRequest limit(int limit);

  /**
   * Set whether to include transactions fields.
   *
   * @param value {@code true} to include transactions, {@code false} to omit
   * @return {@code this}
   */
  @NonNull AccountRequest includeTransaction(boolean value);

  /**
   * Sets the transactionType filter.
   *
   * @param transactionType {@link TransactionType} to be fetched
   * @return {@code this}
   */
  @NonNull AccountRequest transactionType(TransactionType transactionType);

  /**
   * Sets the timestamp criteria filter.
   *
   * @param timestamp list of timestamp criterial params
   * @return {@code this}
   */
  @NonNull AccountRequest timestamp(List<CriteriaParam<Instant>> timestamp);

  /**
   * Gets the CryptoAllowanceQuery request for the account
   *
   * @return instance of {@link AccountCryptoAllowanceRequest}
   */
  @NonNull AccountCryptoAllowanceRequest cryptoAllowance();

  /**
   * Gets the TokenAllowanceQuery request for the account
   *
   * @return instance of {@link AccountTokenAllowanceRequest}
   */
  @NonNull AccountTokenAllowanceRequest tokenAllowance();

  /**
   * Gets the NftAllowanceQuery request for the account
   *
   * @return instance of {@link AccountNftAllowanceRequest}
   */
  @NonNull AccountNftAllowanceRequest nftAllowance();

  /**
   * Gets the NftListQuery request for the account
   *
   * @return instance of {@link AccountNftListRequest}
   */
  @NonNull AccountNftListRequest nftList();

  /**
   * Gets the StakingRewardQuery request for the account
   *
   * @return instance of {@link AccountStakingRewardRequest}
   */
  @NonNull AccountStakingRewardRequest stakingReward();
}
