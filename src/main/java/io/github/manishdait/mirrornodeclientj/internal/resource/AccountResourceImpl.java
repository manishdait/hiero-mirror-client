package io.github.manishdait.mirrornodeclientj.internal.resource;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.AccountResource;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.query.AccountByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.AccountListQuery;
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
