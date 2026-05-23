package io.github.manishdait.hieromirror.internal.resource;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.core.AccountResource;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.query.AccountByIdQuery;
import io.github.manishdait.hieromirror.query.AccountListQuery;
import org.jspecify.annotations.NonNull;

public class AccountResourceImpl implements AccountResource {
  private final MirrorNodeClient client;

  public AccountResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull AccountListQuery findAll() {
    return new AccountListQuery(client);
  }

  @Override
  public @NonNull AccountByIdQuery findById(AccountId accountId) {
    return new AccountByIdQuery(client, accountId);
  }
}
