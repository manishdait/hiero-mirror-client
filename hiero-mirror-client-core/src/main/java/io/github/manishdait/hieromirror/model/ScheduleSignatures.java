package io.github.manishdait.hieromirror.model;

import java.time.Instant;

public record ScheduleSignatures(
    Instant consensusTimestamp, byte[] publicKeyPrefix, byte[] signature, String type) {}
