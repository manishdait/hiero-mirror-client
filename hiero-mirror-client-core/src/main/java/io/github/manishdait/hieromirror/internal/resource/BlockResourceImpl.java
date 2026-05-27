package io.github.manishdait.hieromirror.internal.resource;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.BlockListRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.BlockRequestImpl;
import io.github.manishdait.hieromirror.resource.BlockResource;
import io.github.manishdait.hieromirror.resource.wrapper.BlockListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.BlockRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class BlockResourceImpl implements BlockResource {
  private final MirrorNodeClient client;

  public BlockResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull BlockListRequest findAll() {
    return new BlockListRequestImpl(client);
  }

  @Override
  public @NonNull BlockRequest findByHashOrNumber(final @NonNull String hashOrNumber) {
    Objects.requireNonNull(hashOrNumber, "hashOrNumber must not be null");
    return new BlockRequestImpl(client, hashOrNumber);
  }
}
