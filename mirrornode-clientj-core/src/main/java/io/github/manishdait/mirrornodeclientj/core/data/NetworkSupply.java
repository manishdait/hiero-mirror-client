package io.github.manishdait.mirrornodeclientj.core.data;

import java.time.Instant;

public record NetworkSupply(String releasedSupply, Instant timestamp, String totalSupply) {}
