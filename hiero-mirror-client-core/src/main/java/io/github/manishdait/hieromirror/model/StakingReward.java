package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;
import java.time.Instant;

public record StakingReward(AccountId accountId, long amount, Instant timestamp) {}
