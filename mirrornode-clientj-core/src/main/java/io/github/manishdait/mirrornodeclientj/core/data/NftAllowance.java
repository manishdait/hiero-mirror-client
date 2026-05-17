package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;

public record NftAllowance(
    boolean approvedForAll,
    AccountId owner,
    AccountId payerAccountId,
    AccountId spender,
    TimestampRange timestamp,
    TokenId tokenId) {}
