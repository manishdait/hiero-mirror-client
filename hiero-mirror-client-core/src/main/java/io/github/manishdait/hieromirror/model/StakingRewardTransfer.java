package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;

public record StakingRewardTransfer(AccountId account, long amount) {}
