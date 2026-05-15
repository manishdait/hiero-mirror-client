package io.github.manishdait.mirrornodeclientj.data;

import java.time.Instant;
import java.util.List;

public record NetworkFee(List<Fee> fees, Instant timestamp) {
  public record Fee(long gas, String transactionType) {}
}
