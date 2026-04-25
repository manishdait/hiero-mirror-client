package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.query.AccountListQuery;
import org.jspecify.annotations.NonNull;

public interface AccountResource {
  @NonNull AccountListQuery findAll();

  @NonNull AccountResource findById();
}
