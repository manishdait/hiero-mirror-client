package io.github.manishdait.hieromirror.resource;

import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.resource.wrapper.NftRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NftTransactionHistoryRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TokenListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TokenNftListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TokenRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

/** Access point for executing token related queries. */
public interface TokenResource {
  /** Prepares a query request to fetch list of tokens. */
  @NonNull TokenListRequest findAll();

  /** Prepares a query request to fetch single token by id. */
  default @NonNull TokenRequest findById(final @NonNull String tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return findById(TokenId.fromString(tokenId));
  }

  /** Prepares a query request to fetch single token by id. */
  @NonNull TokenRequest findById(final @NonNull TokenId tokenId);

  /** Prepares a query request to fetch list of nft by tokenId. */
  default @NonNull TokenNftListRequest findNftsByTokenId(final @NonNull String tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return findNftsByTokenId(TokenId.fromString(tokenId));
  }

  /** Prepares a query request to fetch list of nft by tokenId. */
  @NonNull TokenNftListRequest findNftsByTokenId(final @NonNull TokenId tokenId);

  /** Prepares a query request to fetch single nft by nftId. */
  default @NonNull NftRequest findNftsById(final @NonNull String nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    return findNftsById(NftId.fromString(nftId));
  }

  /** Prepares a query request to fetch single nft by tokenId and serial. */
  default @NonNull NftRequest findNftsById(final @NonNull String tokenId, final long serial) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return findNftsById(TokenId.fromString(tokenId), serial);
  }

  /** Prepares a query request to fetch single nft by tokenId and serial. */
  default @NonNull NftRequest findNftsById(final @NonNull TokenId tokenId, final long serial) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return findNftsById(new NftId(tokenId, serial));
  }

  /** Prepares a query request to fetch single nft by nftId. */
  @NonNull NftRequest findNftsById(final @NonNull NftId nftId);

  /** Prepares a query request to fetch nft transaction history by nftId. */
  default @NonNull NftTransactionHistoryRequest findNftsTransactionHistory(
      final @NonNull String nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    return findNftsTransactionHistory(NftId.fromString(nftId));
  }

  /** Prepares a query request to fetch nft transaction history by tokenId and serial. */
  default @NonNull NftTransactionHistoryRequest findNftsTransactionHistory(
      final @NonNull String tokenId, final long serial) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return findNftsTransactionHistory(TokenId.fromString(tokenId), serial);
  }

  /** Prepares a query request to fetch nft transaction history by tokenId and serial. */
  default @NonNull NftTransactionHistoryRequest findNftsTransactionHistory(
      final @NonNull TokenId tokenId, final long serial) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return findNftsTransactionHistory(new NftId(tokenId, serial));
  }

  /** Prepares a query request to fetch nft transaction history by nftId. */
  @NonNull NftTransactionHistoryRequest findNftsTransactionHistory(final @NonNull NftId nftId);
}
