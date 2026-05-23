package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;

public record CustomFee(AccountId accountId, long amount, TokenId denominatingTokenId) {}
