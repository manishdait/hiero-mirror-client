package io.github.manishdait.hieromirror.model;

public enum BalanceModifier {
  CREDIT("credit"),
  DEBIT("debit");

  private final String value;

  BalanceModifier(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
