package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenType;

public record TokenMetadata(
    Key adminKey,
    long decimals,
    String name,
    String symbol,
    TokenId tokenId,
    TokenType tokenType,
    byte[] metadata) {}
