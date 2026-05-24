package io.github.manishdait.hieromirror.internal.resource.wrapper;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.query.BlockQuery;
import io.github.manishdait.hieromirror.resource.wrapper.BlockQueryWrapper;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class BlockQueryWrapperImpl implements BlockQueryWrapper {
  private final MirrorNodeClient client;
  private final String hashOrNumber;

  public BlockQueryWrapperImpl(
      final @NonNull MirrorNodeClient client, final @NonNull String hashOrNumber) {
    Objects.requireNonNull(client, "client must not e null");
    Objects.requireNonNull(hashOrNumber, "hashOrNumber must not be null");

    this.client = client;
    this.hashOrNumber = hashOrNumber;
  }

  @Override
  public @NonNull BlockQuery getQuery() {
    return new BlockQuery().setHash(hashOrNumber);
  }

  @Override
  public @NonNull Optional<Block> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<Block> call(@NonNull Duration timeout) {
    BlockQuery query = getQuery();
    return query.execute(client, timeout);
  }
}
