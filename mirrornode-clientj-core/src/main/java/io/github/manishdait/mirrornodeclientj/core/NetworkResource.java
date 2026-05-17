package io.github.manishdait.mirrornodeclientj.core;

import io.github.manishdait.mirrornodeclientj.core.query.AddressBookQuery;
import io.github.manishdait.mirrornodeclientj.core.query.NetworkExchangeRateQuery;
import io.github.manishdait.mirrornodeclientj.core.query.NetworkFeeQuery;
import io.github.manishdait.mirrornodeclientj.core.query.NetworkStakeInfoQuery;
import io.github.manishdait.mirrornodeclientj.core.query.NetworkSupplyQuery;
import io.github.manishdait.mirrornodeclientj.core.query.RegisteredAddressBookQuery;
import org.jspecify.annotations.NonNull;

public interface NetworkResource {
  @NonNull NetworkFeeQuery fees();

  @NonNull NetworkSupplyQuery supplies();

  @NonNull NetworkExchangeRateQuery exchangeRates();

  @NonNull NetworkStakeInfoQuery stakes();

  @NonNull AddressBookQuery addressBook();

  @NonNull RegisteredAddressBookQuery registeredAddressBook();
}
