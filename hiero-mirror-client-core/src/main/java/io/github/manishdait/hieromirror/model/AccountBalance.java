package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.TokenId;
import java.time.Instant;
import java.util.Map;

public record AccountBalance(Instant timestamp, long balance, Map<TokenId, Long> tokens) {}
