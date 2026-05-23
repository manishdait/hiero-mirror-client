package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.NftTransaction;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.QueryOperator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class NftTransactionHistoryQuery extends Query<Page<NftTransaction>> {
  private NftId nftId;

  private Order order = Order.DESC;
  private int limit = 25;

  private List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  public NftTransactionHistoryQuery() {}

  public NftId getNftId() {
    return nftId;
  }

  public NftTransactionHistoryQuery setNftId(final @NonNull String tokenId, final long serial) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    if (serial < 0) {
      throw new IllegalArgumentException("serial must be greater than positive");
    }

    return setNftId(TokenId.fromString(tokenId), serial);
  }

  public NftTransactionHistoryQuery setNftId(final @NonNull TokenId tokenId, final long serial) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    if (serial < 0) {
      throw new IllegalArgumentException("serial must be greater than positive");
    }

    return setNftId(new NftId(tokenId, serial));
  }

  public NftTransactionHistoryQuery setNftId(final @NonNull String nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    return setNftId(NftId.fromString(nftId));
  }

  public NftTransactionHistoryQuery setNftId(final @NonNull NftId nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    this.nftId = nftId;
    return this;
  }

  public Order getOrder() {
    return order;
  }

  public NftTransactionHistoryQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  public int getLimit() {
    return limit;
  }

  public NftTransactionHistoryQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  public NftTransactionHistoryQuery setTimestamp(
      final @NonNull List<CriteriaParam<Instant>> timestamps) {
    Objects.requireNonNull(timestamps, "timestamps must not be null");
    this.timestamps = new ArrayList<>(timestamps);
    return this;
  }

  public NftTransactionHistoryQuery clearTimestamps() {
    this.timestamps = new ArrayList<>();
    return this;
  }

  public NftTransactionHistoryQuery addTimestamp(
      final @NonNull QueryOperator operator, final @NonNull Instant timestamp) {
    Objects.requireNonNull(operator, "operator must not be null");
    Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.timestamps.add(new CriteriaParam<>(operator, timestamp));
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (nftId == null) {
      throw new IllegalStateException("nftId must be set before executing query");
    }

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(
                client.getBaseUrl()
                    + "/api/v1/tokens/"
                    + nftId.tokenId
                    + "/nfts/"
                    + nftId.serial
                    + "/transactions")
            .method("GET")
            .queryParam("limit", String.valueOf(limit))
            .queryParam("order", order.getValue());

    for (CriteriaParam<Instant> timestamp : timestamps) {
      request.queryParam(
          "timestamp",
          timestamp.getOperator().getValue()
              + ":"
              + timestamp.getValue().getEpochSecond()
              + "."
              + timestamp.getValue().getNano());
    }

    return request.build();
  }

  @Override
  Page<NftTransaction> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseNftTransactions(node);
  }
}
