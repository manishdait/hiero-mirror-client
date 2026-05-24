package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.resource.wrapper.BlockListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.BlockRequest;
import org.jspecify.annotations.NonNull;

/** Access point for executing block related queries. */
public interface BlockResource {
  /** Prepares a query wrapper to fetch list of block. */
  @NonNull BlockListRequest findAll();

  /** Prepares a query wrapper to fetch single block based on the hash or blockNumber. */
  @NonNull BlockRequest findByHashOrNumber(final @NonNull String hashOrNumber);
}
