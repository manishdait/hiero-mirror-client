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

/** Query to get nfts transction history. */
public final class NftTransactionHistoryQuery extends Query<Page<NftTransaction>> {
  private NftId nftId;

  private Order order = Order.DESC;
  private int limit = 25;

  private List<CriteriaParam<Instant>> timestamps = new ArrayList<>();

  /** Constructor. */
  public NftTransactionHistoryQuery() {}

  /**
   * Gets the nftId of the token to fetch.
   *
   * @return the nftId
   */
  public NftId getNftId() {
    return nftId;
  }

  /**
   * Sets the nftId form tokenId and serialNumber.
   *
   * @param tokenId the string representation of tokenId
   * @param serial the serial number of nft
   * @return {@code this}
   * @throws IllegalArgumentException id serial is negative
   */
  public NftTransactionHistoryQuery setNftId(final @NonNull String tokenId, final long serial) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    if (serial < 0) {
      throw new IllegalArgumentException("serial must be greater than positive");
    }

    return setNftId(TokenId.fromString(tokenId), serial);
  }

  /**
   * Sets the nftId form tokenId and serialNumber.
   *
   * @param tokenId the target {@link TokenId} instance
   * @param serial the serial number of nft
   * @return {@code this}
   * @throws IllegalArgumentException id serial is negative
   */
  public NftTransactionHistoryQuery setNftId(final @NonNull TokenId tokenId, final long serial) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    if (serial < 0) {
      throw new IllegalArgumentException("serial must be greater than positive");
    }

    return setNftId(new NftId(tokenId, serial));
  }

  /**
   * Sets the nftId.
   *
   * @param nftId the string representation of nftId
   * @return {@code this}
   */
  public NftTransactionHistoryQuery setNftId(final @NonNull String nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    return setNftId(NftId.fromString(nftId));
  }

  /**
   * Sets the nftId.
   *
   * @param nftId the target {@link NftId} instance
   * @return {@code this}
   */
  public NftTransactionHistoryQuery setNftId(final @NonNull NftId nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    this.nftId = nftId;
    return this;
  }

  /**
   * Gets the sorting order for the query items. Defaults to {@code desc}.
   *
   * @return the {@link Order}
   */
  public Order getOrder() {
    return order;
  }

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  public NftTransactionHistoryQuery setOrder(final @NonNull Order order) {
    Objects.requireNonNull(order, "order must not be null");
    this.order = order;
    return this;
  }

  /**
   * Gets the maximum number of items to be retrieved. Defaults to {@code 25}.
   *
   * @return maximum number of records
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the maximum number of items to return. Must be within range: 1 to 100 inclusive.
   *
   * @param limit maximum items to return
   * @return {@code this}
   * @throws IllegalArgumentException if limit is outside range [1, 100]
   */
  public NftTransactionHistoryQuery setLimit(final int limit) {
    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit must be greater than 0 and less than 100");
    }
    this.limit = limit;
    return this;
  }

  /**
   * Gets the timestamp criteria filter.
   *
   * @return list of timestamp criteria.
   */
  public List<CriteriaParam<Instant>> getTimestamps() {
    return timestamps;
  }

  /**
   * Sets the timestamp criteria filter using a list of timestamps.
   *
   * @param timestamps list of timestamp criterial params
   * @return {@code this}
   */
  public NftTransactionHistoryQuery setTimestamps(
      final @NonNull List<CriteriaParam<Instant>> timestamps) {
    Objects.requireNonNull(timestamps, "timestamps must not be null");
    this.timestamps = new ArrayList<>(timestamps);
    return this;
  }

  /**
   * Clears all timestamp criteria filter.
   *
   * @return {@code this}
   */
  public NftTransactionHistoryQuery clearTimestamps() {
    this.timestamps = new ArrayList<>();
    return this;
  }

  /**
   * Adds a single timestamp criteria to the filter.
   *
   * @param operator the {@link QueryOperator} (e.g., {@link QueryOperator#EQ}, {@link
   *     QueryOperator#GTE})
   * @param timestamp the timestamp to add
   * @return {@code this}
   */
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
