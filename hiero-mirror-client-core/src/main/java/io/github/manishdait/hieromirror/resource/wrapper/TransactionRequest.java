package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.Transaction;
import io.github.manishdait.hieromirror.query.TransactionQuery;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for TransactionQuery. */
public interface TransactionRequest extends QueryRequest<TransactionQuery, Optional<Transaction>> {
  /**
   * Set the nonce filter.
   *
   * @param nonce the nonce value
   * @return {@code this}
   */
  @NonNull TransactionRequest nonce(int nonce);

  /**
   * Set the schedule filter.
   *
   * @param value the boolean value
   * @return {@code this}
   */
  @NonNull TransactionRequest schedule(boolean value);
}
