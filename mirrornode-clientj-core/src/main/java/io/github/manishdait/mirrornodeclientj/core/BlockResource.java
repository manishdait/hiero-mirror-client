package io.github.manishdait.mirrornodeclientj.core;

import io.github.manishdait.mirrornodeclientj.core.query.BlockByHashOrNumberQuery;
import io.github.manishdait.mirrornodeclientj.core.query.BlockListQuery;
import org.jspecify.annotations.NonNull;

public interface BlockResource {
  @NonNull BlockListQuery findAll();

  @NonNull BlockByHashOrNumberQuery findByHashOrNumber(String hashOrNumber);
}
