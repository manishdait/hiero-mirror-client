package io.github.manishdait.mirrornodeclientj.internal.resource;

import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.NetworkResource;
import io.github.manishdait.mirrornodeclientj.query.AddressBookQuery;
import io.github.manishdait.mirrornodeclientj.query.NetworkExchangeRateQuery;
import io.github.manishdait.mirrornodeclientj.query.NetworkFeeQuery;
import io.github.manishdait.mirrornodeclientj.query.NetworkStakeInfoQuery;
import io.github.manishdait.mirrornodeclientj.query.NetworkSupplyQuery;
import io.github.manishdait.mirrornodeclientj.query.RegisteredAddressBookQuery;
import org.jspecify.annotations.NonNull;

public class NetworkResourceImpl implements NetworkResource {
  private final MirrorNodeClient client;

  public NetworkResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull NetworkFeeQuery fees() {
    return new NetworkFeeQuery(client);
  }

  @Override
  public @NonNull NetworkSupplyQuery supplies() {
    return new NetworkSupplyQuery(client);
  }

  @Override
  public @NonNull NetworkExchangeRateQuery exchangeRates() {
    return new NetworkExchangeRateQuery(client);
  }

  @Override
  public @NonNull NetworkStakeInfoQuery stakes() {
    return new NetworkStakeInfoQuery(client);
  }

  @Override
  public @NonNull AddressBookQuery addressBook() {
    return new AddressBookQuery(client);
  }

  @Override
  public @NonNull RegisteredAddressBookQuery registeredAddressBook() {
    return new RegisteredAddressBookQuery(client);
  }
}
