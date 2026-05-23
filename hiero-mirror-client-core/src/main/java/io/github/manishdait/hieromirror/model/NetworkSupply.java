package io.github.manishdait.hieromirror.model;

import java.time.Instant;

public record NetworkSupply(String releasedSupply, Instant timestamp, String totalSupply) {}
