package io.github.manishdait.mirrornodeclientj;

import java.util.Objects;
import org.jspecify.annotations.NonNull;

public enum Operator {
  EQ("eq"),
  GT("gt"),
  GTE("gte"),
  LT("lt"),
  LTE("lte"),
  NE("ne");

  private final String value;

  Operator(String value) {
    this.value = Objects.requireNonNull(value, "value must not be null");
    ;
  }

  public @NonNull String getValue() {
    return this.value;
  }
}
