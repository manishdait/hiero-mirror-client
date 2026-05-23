package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.wrapper.AccountListQueryWrapperImpl;
import io.github.manishdait.hieromirror.internal.wrapper.AccountQueryWrapperImpl;
import io.github.manishdait.hieromirror.resource.wrapper.AccountListQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountQueryWrapper;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountResource {
  private final MirrorNodeClient client;

  public AccountResource(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must nor be null");
    this.client = client;
  }

  public AccountListQueryWrapper findAll() {
    return new AccountListQueryWrapperImpl(client);
  }

  public AccountQueryWrapper findById(String idOrAliasOrEvmAddress) {
    return new AccountQueryWrapperImpl(client, idOrAliasOrEvmAddress);
  }
}
