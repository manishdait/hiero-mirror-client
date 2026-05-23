package io.github.manishdait.mirrornodeclientj.core;

import io.github.manishdait.hieromirror.query.AddressBookQuery;
import io.github.manishdait.hieromirror.query.NetworkExchangeRateQuery;
import io.github.manishdait.hieromirror.query.NetworkFeeQuery;
import io.github.manishdait.hieromirror.query.NetworkStakeInfoQuery;
import io.github.manishdait.hieromirror.query.NetworkSupplyQuery;
import io.github.manishdait.hieromirror.query.RegisteredAddressBookQuery;
import org.jspecify.annotations.NonNull;

public interface NetworkResource {
  @NonNull NetworkFeeQuery fees();

  @NonNull NetworkSupplyQuery supplies();

  @NonNull NetworkExchangeRateQuery exchangeRates();

  @NonNull NetworkStakeInfoQuery stakes();

  @NonNull AddressBookQuery addressBook();

  @NonNull RegisteredAddressBookQuery registeredAddressBook();
}
