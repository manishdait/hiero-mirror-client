package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.query.AddressBookQuery;
import io.github.manishdait.mirrornodeclientj.query.NetworkExchangeRateQuery;
import io.github.manishdait.mirrornodeclientj.query.NetworkFeeQuery;
import io.github.manishdait.mirrornodeclientj.query.NetworkStakeInfoQuery;
import io.github.manishdait.mirrornodeclientj.query.NetworkSupplyQuery;
import io.github.manishdait.mirrornodeclientj.query.RegisteredAddressBookQuery;
import org.jspecify.annotations.NonNull;

public interface NetworkResource {
  @NonNull NetworkFeeQuery fees();

  @NonNull NetworkSupplyQuery supplies();

  @NonNull NetworkExchangeRateQuery exchangeRates();

  @NonNull NetworkStakeInfoQuery stakes();

  @NonNull AddressBookQuery addressBook();

  @NonNull RegisteredAddressBookQuery registeredAddressBook();
}
