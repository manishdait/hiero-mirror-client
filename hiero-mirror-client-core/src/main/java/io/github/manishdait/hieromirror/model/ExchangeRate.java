package io.github.manishdait.hieromirror.model;

import java.time.Instant;

public record ExchangeRate(Rate currentRate, Rate nextRate, Instant timestamp) {
  public record Rate(int centEquivalent, long expirationTime, int hbar_equivalent) {}
}
