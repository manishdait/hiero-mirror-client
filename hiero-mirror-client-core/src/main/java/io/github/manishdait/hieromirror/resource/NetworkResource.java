package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.resource.wrapper.NetworkExchangeRateRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkFeeRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkStakingInfoRequest;
import org.jspecify.annotations.NonNull;

public interface NetworkResource {
  @NonNull NetworkExchangeRateRequest exchangeRates();

  @NonNull NetworkFeeRequest fees();

  @NonNull NetworkStakingInfoRequest stakingInfo();
}
