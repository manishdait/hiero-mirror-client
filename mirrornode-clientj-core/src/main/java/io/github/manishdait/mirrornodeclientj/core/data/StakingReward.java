package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;
import java.time.Instant;

public record StakingReward(AccountId accountId, long amount, Instant timestamp) {}
