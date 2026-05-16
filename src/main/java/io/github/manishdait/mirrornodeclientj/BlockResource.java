package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.query.BlockByHashOrNumberQuery;
import io.github.manishdait.mirrornodeclientj.query.BlockListQuery;
import org.jspecify.annotations.NonNull;

public interface BlockResource {
  @NonNull BlockListQuery findAll();

  @NonNull BlockByHashOrNumberQuery findByHashOrNumber(String hashOrNumber);
}
