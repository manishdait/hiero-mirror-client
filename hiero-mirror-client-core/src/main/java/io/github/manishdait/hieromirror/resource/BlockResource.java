package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.resource.wrapper.BlockListQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.BlockQueryWrapper;

public interface BlockResource {
  BlockListQueryWrapper findAll();

  BlockQueryWrapper findByHashOrNumber();
}
