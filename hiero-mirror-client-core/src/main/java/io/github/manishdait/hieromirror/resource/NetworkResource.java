package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.resource.wrapper.NetworkAddressBookRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkExchangeRateRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkFeeRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkRegisteredAddressBookRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkStakingInfoRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkSupplyRequest;
import org.jspecify.annotations.NonNull;

public interface NetworkResource {
  @NonNull NetworkExchangeRateRequest exchangeRates();

  @NonNull NetworkFeeRequest fees();

  @NonNull NetworkStakingInfoRequest stakingInfo();

  @NonNull NetworkSupplyRequest supplies();

  @NonNull NetworkAddressBookRequest addressBook();

  @NonNull NetworkRegisteredAddressBookRequest registeredAddressBook();
}
