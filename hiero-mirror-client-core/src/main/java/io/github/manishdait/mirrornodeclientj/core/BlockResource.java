package io.github.manishdait.mirrornodeclientj.core;

import io.github.manishdait.hieromirror.query.BlockByHashOrNumberQuery;
import io.github.manishdait.hieromirror.query.BlockListQuery;
import org.jspecify.annotations.NonNull;

public interface BlockResource {
  @NonNull BlockListQuery findAll();

  @NonNull BlockByHashOrNumberQuery findByHashOrNumber(String hashOrNumber);
}
