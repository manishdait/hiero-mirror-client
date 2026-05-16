package io.github.manishdait.mirrornodeclientj.internal.resource;

import io.github.manishdait.mirrornodeclientj.BlockResource;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.query.BlockByHashOrNumberQuery;
import io.github.manishdait.mirrornodeclientj.query.BlockListQuery;
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
