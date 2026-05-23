package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;

public record TokenTransfer(TokenId tokenId, AccountId account, long amount, boolean isApproval) {}
