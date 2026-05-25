package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountNftListQuery;
import io.github.manishdait.hieromirror.resource.wrapper.AccountNftListRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class AccountNftListRequestImpl implements AccountNftListRequest {
  private final MirrorNodeClient client;
  private final String idOrAliasOrEvmAddress;

  private Order order;
  private Integer limit;
  private CriteriaParam<AccountId> senderId;
  private CriteriaParam<TokenId> tokenId;
  private CriteriaParam<Long> serialNumber;

  public AccountNftListRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String idOrAliasOrEvmAddress) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(idOrAliasOrEvmAddress, "idOrAliasOrEvmAddress must not be null");

    this.client = client;
    this.idOrAliasOrEvmAddress = idOrAliasOrEvmAddress;
  }

  @Override
  public @NonNull AccountNftListRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull AccountNftListRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull AccountNftListRequest senderId(CriteriaParam<AccountId> senderId) {
    this.senderId = senderId;
    return this;
  }

  @Override
  public @NonNull AccountNftListRequest tokenId(CriteriaParam<TokenId> tokenId) {
    this.tokenId = tokenId;
    return this;
  }

  @Override
  public @NonNull AccountNftListRequest serialNumber(CriteriaParam<Long> serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  @Override
  public @NonNull AccountNftListQuery buildQuery() {
    AccountNftListQuery query = new AccountNftListQuery().setAlias(idOrAliasOrEvmAddress);

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (senderId != null) {
      query.setSpenderId(senderId.getOperator(), senderId.getValue());
    }

    if (tokenId != null) {
      query.setTokenId(tokenId.getOperator(), tokenId.getValue());
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
    AccountNftListQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
