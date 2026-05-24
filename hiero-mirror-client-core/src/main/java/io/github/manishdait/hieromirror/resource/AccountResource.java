package io.github.manishdait.hieromirror.resource;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.EvmAddress;
import io.github.manishdait.hieromirror.resource.wrapper.AccountListQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountQueryWrapper;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

/** Access point for executing account related queries. */
public interface AccountResource {

  /** Prepares a query wrapper to fetch list of accounts. */
  @NonNull AccountListQueryWrapper findAll();

  /** Prepares a query wrapper to fetch a single account by AccountId. */
  default @NonNull AccountQueryWrapper findByIdOrAliasOrEvmAddress(
      final @NonNull AccountId accountId) {
    Objects.requireNonNull(accountId, "accountId must not be null");
    return findByIdOrAliasOrEvmAddress(accountId.toString());
  }

  /** Prepares a query wrapper to fetch a single account by EvmAddress. */
  default @NonNull AccountQueryWrapper findByIdOrAliasOrEvmAddress(
      final @NonNull EvmAddress evmAddress) {
    Objects.requireNonNull(evmAddress, "evmAddress must not be null");
    return findByIdOrAliasOrEvmAddress(evmAddress.toString());
  }

  /**
   * Prepares a query wrapper to fetch a single account by raw string AccountId or EvmAddress or
   * Alias.
   */
  @NonNull AccountQueryWrapper findByIdOrAliasOrEvmAddress(
      final @NonNull String idOrAliasOrEvmAddress);
}
