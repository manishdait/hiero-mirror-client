package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.resource.wrapper.TransactionListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TransactionRequest;
import org.jspecify.annotations.NonNull;

/** Access point for executing transaction related queries. */
public interface TransactionResource {
  /** Prepares a query request to fetch list of transaction. */
  @NonNull TransactionListRequest findAll();

  /** Prepares a query request to fetch single transaction by id. */
  @NonNull TransactionRequest findById(final @NonNull String transactionId);
}
