package io.github.manishdait.mirrornodeclientj.data;

import java.time.Instant;

public record NetworkSupply(String releasedSupply, Instant timestamp, String totalSupply) {}
