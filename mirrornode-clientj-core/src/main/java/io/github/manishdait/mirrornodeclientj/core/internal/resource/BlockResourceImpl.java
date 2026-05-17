package io.github.manishdait.mirrornodeclientj.core.internal.resource;

import io.github.manishdait.mirrornodeclientj.core.BlockResource;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.query.BlockByHashOrNumberQuery;
import io.github.manishdait.mirrornodeclientj.core.query.BlockListQuery;
import org.jspecify.annotations.NonNull;

public class BlockResourceImpl implements BlockResource {
  private final MirrorNodeClient client;

  public BlockResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull BlockListQuery findAll() {
    return new BlockListQuery(client);
  }

  @Override
  public @NonNull BlockByHashOrNumberQuery findByHashOrNumber(String hashOrNumber) {
    return new BlockByHashOrNumberQuery(client, hashOrNumber);
  }
}
