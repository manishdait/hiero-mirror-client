package io.github.manishdait.hieromirror.model;

import org.jspecify.annotations.NonNull;

public class CriteriaParam<T> {
  private final QueryOperator operator;
  private final T value;

  public CriteriaParam(final @NonNull QueryOperator operator, final @NonNull T value) {
    this.operator = operator;
    this.value = value;
  }

  public QueryOperator getOperator() {
    return operator;
  }

  public T getValue() {
    return value;
  }
}
