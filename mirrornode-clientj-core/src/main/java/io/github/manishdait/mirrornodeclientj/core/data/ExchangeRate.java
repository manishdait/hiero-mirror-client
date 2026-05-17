package io.github.manishdait.mirrornodeclientj.core.data;

import java.time.Instant;

public record ExchangeRate(Rate currentRate, Rate nextRate, Instant timestamp) {
  public record Rate(int centEquivalent, long expirationTime, int hbar_equivalent) {}
}
