package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.resource.wrapper.AccountListQueryWrapper;
import io.github.manishdait.hieromirror.resource.wrapper.AccountQueryWrapper;

public interface AccountResource {
  AccountListQueryWrapper findAll();
  AccountQueryWrapper findById(String idOrAliasOrEvmAddress);
}
