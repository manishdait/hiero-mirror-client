package io.github.manishdait.hieromirror.internal.resource;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.AccountListQueryWrapperImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.AccountQueryWrapperImpl;
import io.github.manishdait.hieromirror.resource.AccountResource;
import io.github.manishdait.hieromirror.resource.wrapper.AccountListQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountQueryWrapper;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class AccountResourceImpl implements AccountResource {
  private final MirrorNodeClient client;

  public AccountResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must nor be null");
    this.client = client;
  }

  @Override
  public AccountListQueryWrapper findAll() {
    return new AccountListQueryWrapperImpl(client);
  }

  @Override
  public AccountQueryWrapper findById(String idOrAliasOrEvmAddress) {
    return new AccountQueryWrapperImpl(client, idOrAliasOrEvmAddress);
  }
}
