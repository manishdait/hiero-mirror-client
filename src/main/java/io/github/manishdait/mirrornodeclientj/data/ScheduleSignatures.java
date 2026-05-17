package io.github.manishdait.mirrornodeclientj.data;

import java.time.Instant;

public record ScheduleSignatures(
    Instant consensusTimestamp, byte[] publicKeyPrefix, byte[] signature, String type) {}
