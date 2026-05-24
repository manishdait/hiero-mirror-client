package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.Nft;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

/** Query to get information for a non-fungible token */
public final class NftQuery extends Query<Optional<Nft>> {
  private NftId nftId;

  /** Constructor. */
  public NftQuery() {}

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
  public NftQuery setNftId(final @NonNull String tokenId, final long serial) {
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
  public NftQuery setNftId(final @NonNull TokenId tokenId, final long serial) {
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
  public NftQuery setNftId(final @NonNull String nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    return setNftId(NftId.fromString(nftId));
  }

  /**
   * Sets the nftId.
   *
   * @param nftId the target {@link NftId} instance
   * @return {@code this}
   */
  public NftQuery setNftId(final @NonNull NftId nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    this.nftId = nftId;
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
            .url(client.getBaseUrl() + "/api/v1/tokens/" + nftId.tokenId + "/nfts/" + nftId.serial)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<Nft> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseNft(node);
  }
}
