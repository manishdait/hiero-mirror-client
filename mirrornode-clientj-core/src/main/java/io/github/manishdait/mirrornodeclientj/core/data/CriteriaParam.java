package io.github.manishdait.mirrornodeclientj.core.data;

import org.jspecify.annotations.NonNull;

public class CriteriaParam<T> {
  private final Operator operator;
  private final T value;

  public CriteriaParam(final @NonNull Operator operator, final @NonNull T value) {
    this.operator = operator;
    this.value = value;
  }

  public Operator getOperator() {
    return operator;
  }

  public T getValue() {
    return value;
  }
}
