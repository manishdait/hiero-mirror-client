package io.github.manishdait.mirrornodeclientj.core.data;

import java.util.List;

public record Page<T>(List<T> data, String next) {
  public boolean hasNext() {
    return this.next != null;
  }
}
