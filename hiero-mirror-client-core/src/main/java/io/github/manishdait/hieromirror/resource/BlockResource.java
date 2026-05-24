package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.resource.wrapper.BlockListQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.BlockQueryWrapper;
import org.jspecify.annotations.NonNull;

/** Access point for executing block related queries. */
public interface BlockResource {
  /** Prepares a query wrapper to fetch list of block. */
  @NonNull BlockListQueryWrapper findAll();

  /** Prepares a query wrapper to fetch single block based on the hash or blockNumber. */
  @NonNull BlockQueryWrapper findByHashOrNumber(final @NonNull String hashOrNumber);
}
