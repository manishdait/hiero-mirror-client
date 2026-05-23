package io.github.manishdait.hieromirror.internal.resource;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.NetworkResource;
import io.github.manishdait.hieromirror.query.AddressBookQuery;
import io.github.manishdait.hieromirror.query.NetworkExchangeRateQuery;
import io.github.manishdait.hieromirror.query.NetworkFeeQuery;
import io.github.manishdait.hieromirror.query.NetworkStakeInfoQuery;
import io.github.manishdait.hieromirror.query.NetworkSupplyQuery;
import io.github.manishdait.hieromirror.query.RegisteredAddressBookQuery;
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
