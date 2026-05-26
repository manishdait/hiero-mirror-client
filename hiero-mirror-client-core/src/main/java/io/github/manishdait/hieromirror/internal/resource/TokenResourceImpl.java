package io.github.manishdait.hieromirror.internal.resource;

import com.hedera.hashgraph.sdk.NftId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.NftRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.NftTransactionHistoryRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.TokenListRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.TokenNftListRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.TokenRequestImpl;
import io.github.manishdait.hieromirror.resource.TokenResource;
import io.github.manishdait.hieromirror.resource.wrapper.NftRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NftTransactionHistoryRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TokenListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TokenNftListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.TokenRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class TokenResourceImpl implements TokenResource {
  private final MirrorNodeClient client;

  public TokenResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must nor be null");
    this.client = client;
  }

  @Override
  public @NonNull TokenListRequest findAll() {
    return new TokenListRequestImpl(client);
  }

  @Override
  public @NonNull TokenRequest findById(@NonNull TokenId tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return new TokenRequestImpl(client, tokenId);
  }

  @Override
  public @NonNull TokenNftListRequest findNftsByTokenId(@NonNull TokenId tokenId) {
    Objects.requireNonNull(tokenId, "tokenId must not be null");
    return new TokenNftListRequestImpl(client, tokenId);
  }

  @Override
  public @NonNull NftRequest findNftsById(@NonNull NftId nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    return new NftRequestImpl(client, nftId);
  }

  @Override
  public @NonNull NftTransactionHistoryRequest findNftsTransactionHistory(@NonNull NftId nftId) {
    Objects.requireNonNull(nftId, "nftId must not be null");
    return new NftTransactionHistoryRequestImpl(client, nftId);
  }
}
