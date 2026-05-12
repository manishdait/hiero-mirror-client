package io.github.manishdait.mirrornodeclientj.internal.resource;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.AccountResource;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.query.GetAccountByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.GetAccountListQuery;
import org.jspecify.annotations.NonNull;

public class AccountResourceImpl implements AccountResource {
  private final MirrorNodeClient client;

  public AccountResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull GetAccountListQuery findAll() {
    return new GetAccountListQuery(client);
  }

  @Override
  public @NonNull GetAccountByIdQuery findById(AccountId accountId) {
    return new GetAccountByIdQuery(client, accountId);
  }
}
