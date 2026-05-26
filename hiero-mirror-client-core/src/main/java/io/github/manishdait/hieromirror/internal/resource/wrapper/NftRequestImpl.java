package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.NftId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.query.NftQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NftRequest;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class NftRequestImpl implements NftRequest {
  private final MirrorNodeClient client;
  private final NftId nftId;

  public NftRequestImpl(final @NonNull MirrorNodeClient client, final @NonNull NftId nftId) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(nftId, "nftId must not be null");

    this.client = client;
    this.nftId = nftId;
  }

  @Override
  public @NonNull NftQuery buildQuery() {
    return new NftQuery().setNftId(nftId);
  }

  @Override
  public @NonNull Optional<Nft> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<Nft> call(@NonNull Duration timeout) {
    NftQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
