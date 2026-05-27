package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.hieromirror.model.BalanceModifier;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.Transaction;
import io.github.manishdait.hieromirror.model.TransactionResult;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.TransactionListQuery;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for TransactionListQuery. */
public interface TransactionListRequest
    extends QueryRequest<TransactionListQuery, Page<Transaction>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull TransactionListRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull TransactionListRequest order(Order order);

  /**
   * Sets the accountId criteria param.
   *
   * @param accountId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull TransactionListRequest accountId(CriteriaParam<AccountId> accountId);

  /**
   * Sets the timestamp criteria filter.
   *
   * @param timestamp list of timestamp criterial params
   * @return {@code this}
   */
  @NonNull TransactionListRequest timestamp(List<CriteriaParam<Instant>> timestamp);

  /**
   * Sets the transactionType filter.
   *
   * @param type the {@link TransactionType}
   * @return {@code this}
   */
  @NonNull TransactionListRequest transactionType(TransactionType type);

  /**
   * Sets the transactionResult filter.
   *
   * @param result the {@link TransactionResult}
   * @return {@code this}
   */
  @NonNull TransactionListRequest result(TransactionResult result);

  /**
   * Sets the balance modifier filter.
   *
   * @param modifier the {@link BalanceModifier}
   * @return {@code this}
   */
  @NonNull TransactionListRequest type(BalanceModifier modifier);
}
