package io.github.manishdait.mirrornodeclientj.resource;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.AccountResource;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.query.AccountListQuery;
import io.github.manishdait.mirrornodeclientj.query.AccountQuery;
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
  public @NonNull AccountQuery findById(AccountId accountId) {
    return new AccountQuery(client, accountId);
  }
}
