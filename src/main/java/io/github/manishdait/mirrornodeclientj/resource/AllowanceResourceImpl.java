package io.github.manishdait.mirrornodeclientj.resource;

import com.hedera.hashgraph.sdk.AccountId;
import io.github.manishdait.mirrornodeclientj.AllowanceResource;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.query.CryptoAllowanceQuery;
import io.github.manishdait.mirrornodeclientj.query.NftAllowanceQuery;
import io.github.manishdait.mirrornodeclientj.query.TokenAllowanceQuery;
import org.jspecify.annotations.NonNull;

public class AllowanceResourceImpl implements AllowanceResource {
  private final MirrorNodeClient client;

  public AllowanceResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull CryptoAllowanceQuery getCryptoAllowance(AccountId accountId) {
    return new CryptoAllowanceQuery(client, accountId);
  }

  @Override
  public @NonNull TokenAllowanceQuery getTokenAllowance(AccountId accountId) {
    return new TokenAllowanceQuery(client, accountId);
  }

  @Override
  public @NonNull NftAllowanceQuery getNftAllowance(AccountId accountId) {
    return new NftAllowanceQuery(client, accountId);
  }
}
