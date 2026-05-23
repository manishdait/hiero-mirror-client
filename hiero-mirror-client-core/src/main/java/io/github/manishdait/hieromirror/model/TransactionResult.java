package io.github.manishdait.hieromirror.model;

public enum TransactionResult {
  SUCCESS("success"),
  FAIL("fail");

  private final String value;

  TransactionResult(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
