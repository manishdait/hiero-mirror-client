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

  public @Nullable String getIdOrAliasOrEvmAddress() {
    return identifier;
  }

  @SuppressWarnings("unchecked")
  Q setIdentifier(final @NonNull String identifier) {
    Objects.requireNonNull(identifier, "identifier must not be null");
    this.identifier = identifier;
    return (Q) this;
  }

  public Q setAccountId(final @NonNull AccountId accountId) {
    Objects.requireNonNull(accountId, "accountId must not be null");
    return setIdentifier(accountId.toString());
  }

  public Q setEvmAddress(final @NonNull EvmAddress evmAddress) {
    Objects.requireNonNull(evmAddress, "evmAddress must not be null");
    return setIdentifier(evmAddress.toString());
  }

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
