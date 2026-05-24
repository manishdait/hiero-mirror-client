package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.EvmAddress;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/** Abstract base class for Account Identifier based Query. */
abstract class AccountIdentifierQuery<Q, T> extends Query<T> {
  protected String identifier;

  /**
   * Gets the identifier `IdOrAliasOrEvmAddress` for the query.
   *
   * @return the identifier for query
   */
  public @Nullable String getIdOrAliasOrEvmAddress() {
    return identifier;
  }

  /**
   * Set the identifier `IdOrAliasOrEvmAddress` for the query.
   *
   * @param identifier the identifier for the query
   * @return {@code this}
   */
  @SuppressWarnings("unchecked")
  Q setIdentifier(final @NonNull String identifier) {
    Objects.requireNonNull(identifier, "identifier must not be null");
    this.identifier = identifier;
    return (Q) this;
  }

  /**
   * Sets accountId as identifier for the query.
   *
   * @param accountId the accountId
   * @return {@code this}
   */
  public Q setAccountId(final @NonNull String accountId) {
    Objects.requireNonNull(accountId, "accountId must not be null");
    return setAccountId(AccountId.fromString(accountId));
  }

  /**
   * Sets accountId as identifier for the query.
   *
   * @param accountId the accountId
   * @return {@code this}
   */
  public Q setAccountId(final @NonNull AccountId accountId) {
    Objects.requireNonNull(accountId, "accountId must not be null");
    return setIdentifier(accountId.toString());
  }

  /**
   * Sets evmAddress as identifier for the query.
   *
   * @param evmAddress the evmAddress of account
   * @return {@code this}
   */
  public Q setEvmAddress(final @NonNull String evmAddress) {
    Objects.requireNonNull(evmAddress, "evmAddress must not be null");
    return setEvmAddress(EvmAddress.fromString(evmAddress));
  }

  /**
   * Sets evmAddress as identifier for the query.
   *
   * @param evmAddress the evmAddress of account
   * @return {@code this}
   */
  public Q setEvmAddress(final @NonNull EvmAddress evmAddress) {
    Objects.requireNonNull(evmAddress, "evmAddress must not be null");
    return setIdentifier(evmAddress.toString());
  }

  /**
   * Sets alias as identifier for the query.
   *
   * @param alias the alias for account
   * @return {@code this}
   */
  public Q setAlias(final @NonNull String alias) {
    Objects.requireNonNull(alias, "alias must not be null");
    return setIdentifier(alias);
  }

  abstract MirrorNodeRequest buildRequestInternal(final @NonNull MirrorNodeClient client);

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (identifier == null) {
      throw new IllegalStateException(
          "account id or alias or evmAddress must be set before executing query");
    }

    return this.buildRequestInternal(client);
  }
}
