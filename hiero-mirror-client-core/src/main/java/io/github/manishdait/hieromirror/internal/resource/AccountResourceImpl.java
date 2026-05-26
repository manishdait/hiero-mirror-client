package io.github.manishdait.hieromirror.internal.resource;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.AccountListRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.AccountRequestImpl;
import io.github.manishdait.hieromirror.resource.AccountResource;
import io.github.manishdait.hieromirror.resource.wrapper.AccountListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.AccountRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountResourceImpl implements AccountResource {
  private final MirrorNodeClient client;

  public AccountResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must nor be null");
    this.client = client;
  }

  @Override
  public @NonNull AccountListRequest findAll() {
    return new AccountListRequestImpl(client);
  }

  @Override
  public @NonNull AccountRequest findByIdOrAliasOrEvmAddress(
      @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");
    return new AccountRequestImpl(client, idOrAliasOrEvmAddress);
  }
}
