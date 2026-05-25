package io.github.manishdait.hieromirror.resource;

import io.github.manishdait.hieromirror.resource.wrapper.NetworkAddressBookRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkExchangeRateRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkFeeRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkRegisteredAddressBookRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkStakingInfoRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkSupplyRequest;
import org.jspecify.annotations.NonNull;

/** Access point for executing network related queries. */
public interface NetworkResource {
  /** Prepares a query request to fetch network exchange rates. */
  @NonNull NetworkExchangeRateRequest exchangeRates();

  /** Prepares a query request to fetch network fess. */
  @NonNull NetworkFeeRequest fees();

  /** Prepares a query request to fetch network staking information. */
  @NonNull NetworkStakingInfoRequest stakingInfo();

  /** Prepares a query request to fetch network supplies. */
  @NonNull NetworkSupplyRequest supplies();

  /** Prepares a query request to fetch network address book. */
  @NonNull NetworkAddressBookRequest addressBook();

  /** Prepares a query request to fetch network registered address book. */
  @NonNull NetworkRegisteredAddressBookRequest registeredAddressBook();
}
