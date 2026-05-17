package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;

public record Transfer(AccountId account, long amount, boolean isApproval) {}
