package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.TokenId;
import java.time.Instant;

public record TokenRelationShip(
    boolean automaticAssociation,
    long balance,
    Instant createdTimestamp,
    long decimals,
    TokenFreezeStatus freezeStatus,
    TokenKycStatus kycStatus,
    TokenId tokenId) {}
