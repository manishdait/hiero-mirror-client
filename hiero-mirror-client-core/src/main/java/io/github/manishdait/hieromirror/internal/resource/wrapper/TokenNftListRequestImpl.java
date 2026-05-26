package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.TokenNftListQuery;
import io.github.manishdait.hieromirror.resource.wrapper.TokenNftListRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class TokenNftListRequestImpl implements TokenNftListRequest {
  private final MirrorNodeClient client;
  private final TokenId tokenId;

  private Integer limit;
  private Order order;
  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<Long> serialNumber;

  public TokenNftListRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull TokenId tokenId) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(tokenId, "tokenId must not be null");

    this.client = client;
    this.tokenId = tokenId;
  }

  @Override
  public @NonNull TokenNftListRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull TokenNftListRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull TokenNftListRequest accountId(CriteriaParam<AccountId> accountId) {
    this.accountId = accountId;
    return this;
  }

  @Override
  public @NonNull TokenNftListRequest serialNumber(CriteriaParam<Long> serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  @Override
  public @NonNull TokenNftListQuery buildQuery() {
    TokenNftListQuery query = new TokenNftListQuery().setTokenId(tokenId);

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (accountId != null) {
      query.setAccountId(accountId.getOperator(), accountId.getValue());
    }

    if (serialNumber != null) {
      query.setSerialNumber(serialNumber.getOperator(), serialNumber.getValue());
    }

    return query;
  }

  @Override
  public @NonNull Page<Nft> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<Nft> call(@NonNull Duration timeout) {
    TokenNftListQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
