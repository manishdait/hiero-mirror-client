package io.github.manishdait.mirrornodeclientj.core.data;

import java.util.Objects;
import org.jspecify.annotations.NonNull;

public enum Order {
  ASC("asc"),
  DESC("desc");

  private final String value;

  Order(String value) {
    this.value = Objects.requireNonNull(value, "value must not be null");
    ;
  }

  public @NonNull String getValue() {
    return this.value;
  }
}
