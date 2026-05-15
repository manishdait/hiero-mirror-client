package io.github.manishdait.mirrornodeclientj.data;

public record StakeInfo(
    long maxStakeRewarded,
    long maxStakingRewardRatePerHbar,
    long maxTotalReward,
    float nodeRewardFeeFraction,
    long reservedStakingRewards,
    long rewardBalanceThreshold,
    long stakeTotal,
    TimestampRange stakingPeriod,
    long stakingPeriodDuration,
    long stakingPeriodsStored,
    float stakingRewardFeeFraction,
    long stakingRewardRate,
    long stakingStartThreshold,
    long unreservedStakingRewardBalance) {}
