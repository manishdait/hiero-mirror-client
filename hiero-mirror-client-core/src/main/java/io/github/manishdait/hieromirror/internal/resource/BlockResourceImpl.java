package io.github.manishdait.hieromirror.internal.resource;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.BlockListQueryWrapperImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.BlockQueryWrapperImpl;
import io.github.manishdait.hieromirror.resource.BlockResource;
import io.github.manishdait.hieromirror.resource.wrapper.BlockListQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.BlockQueryWrapper;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class BlockResourceImpl implements BlockResource {
  private final MirrorNodeClient client;

  public BlockResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must nor be null");
    this.client = client;
  }

  @Override
  public @NonNull BlockListQueryWrapper findAll() {
    return new BlockListQueryWrapperImpl(client);
  }

  @Override
  public @NonNull BlockQueryWrapper findByHashOrNumber(final @NonNull String hashOrNumber) {
    Objects.requireNonNull(hashOrNumber, "hashOrNumber must not be null");
    return new BlockQueryWrapperImpl(client, hashOrNumber);
  }
}
