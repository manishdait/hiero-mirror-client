package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.NftId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NftTransaction;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.NftTransactionHistoryQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NftTransactionHistoryRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class NftTransactionHistoryRequestImpl implements NftTransactionHistoryRequest {
  private final MirrorNodeClient client;
  private final NftId nftId;

  private Integer limit;
  private Order order;
  private List<CriteriaParam<Instant>> timestamp;

  public NftTransactionHistoryRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull NftId nftId) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(nftId, "nftId must not be null");

    this.client = client;
    this.nftId = nftId;
  }

  @Override
  public @NonNull NftTransactionHistoryRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull NftTransactionHistoryRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull NftTransactionHistoryRequest timestamp(List<CriteriaParam<Instant>> timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public @NonNull NftTransactionHistoryQuery buildQuery() {
    NftTransactionHistoryQuery query = new NftTransactionHistoryQuery().setNftId(nftId);

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (timestamp != null) {
      query.setTimestamps(timestamp);
    }

    return query;
  }

  @Override
  public @NonNull Page<NftTransaction> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<NftTransaction> call(@NonNull Duration timeout) {
    NftTransactionHistoryQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
