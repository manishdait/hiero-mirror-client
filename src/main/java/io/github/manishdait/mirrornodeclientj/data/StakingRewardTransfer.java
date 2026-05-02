package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;

public record StakingRewardTransfer(AccountId account, long amount) {}
