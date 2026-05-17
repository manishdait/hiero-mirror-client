package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;

public record StakingRewardTransfer(AccountId account, long amount) {}
