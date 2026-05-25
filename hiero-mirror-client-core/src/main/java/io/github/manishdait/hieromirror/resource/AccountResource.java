package io.github.manishdait.hieromirror.resource;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.EvmAddress;
import io.github.manishdait.hieromirror.resource.wrapper.AccountListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.AccountRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

/** Access point for executing account related queries. */
public interface AccountResource {

  /** Prepares a query request to fetch list of accounts. */
  @NonNull AccountListRequest findAll();

  /** Prepares a query request to fetch a single account by AccountId. */
  default @NonNull AccountRequest findByIdOrAliasOrEvmAddress(final @NonNull AccountId accountId) {
    Objects.requireNonNull(accountId, "accountId must not be null");
    return findByIdOrAliasOrEvmAddress(accountId.toString());
  }

  /** Prepares a query request to fetch a single account by EvmAddress. */
  default @NonNull AccountRequest findByIdOrAliasOrEvmAddress(
      final @NonNull EvmAddress evmAddress) {
    Objects.requireNonNull(evmAddress, "evmAddress must not be null");
    return findByIdOrAliasOrEvmAddress(evmAddress.toString());
  }

  /**
   * Prepares a query request to fetch a single account by raw string AccountId or EvmAddress or
   * Alias.
   */
  @NonNull AccountRequest findByIdOrAliasOrEvmAddress(final @NonNull String idOrAliasOrEvmAddress);
}
