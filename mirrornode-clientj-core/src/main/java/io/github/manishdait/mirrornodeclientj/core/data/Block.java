package io.github.manishdait.mirrornodeclientj.core.data;

public record Block(
    Long count,
    Long gasUsed,
    String hapiVersion,
    String hash,
    String logsBloom,
    String name,
    Long number,
    String previousHash,
    Long size,
    TimestampRange timestamp) {}
