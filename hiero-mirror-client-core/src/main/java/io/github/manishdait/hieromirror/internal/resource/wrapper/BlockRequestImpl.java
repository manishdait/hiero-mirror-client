package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.query.BlockQuery;
import io.github.manishdait.hieromirror.resource.wrapper.BlockRequest;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class BlockRequestImpl implements BlockRequest {
  private final MirrorNodeClient client;
  private final String hashOrNumber;

  public BlockRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String hashOrNumber) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(hashOrNumber, "hashOrNumber must not be null");

    this.client = client;
    this.hashOrNumber = hashOrNumber;
  }

  @Override
  public @NonNull BlockQuery buildQuery() {
    return new BlockQuery().setHash(hashOrNumber);
  }

  @Override
  public @NonNull Optional<Block> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<Block> call(@NonNull Duration timeout) {
    BlockQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
