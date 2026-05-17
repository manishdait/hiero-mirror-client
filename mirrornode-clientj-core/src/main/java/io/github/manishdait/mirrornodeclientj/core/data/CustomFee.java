package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;

public record CustomFee(AccountId accountId, long amount, TokenId denominatingTokenId) {}
