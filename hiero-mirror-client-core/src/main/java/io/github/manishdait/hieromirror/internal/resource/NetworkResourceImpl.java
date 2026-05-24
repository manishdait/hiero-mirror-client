package io.github.manishdait.hieromirror.internal.resource;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.NetworkAddressBookRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.NetworkExchangeRateRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.NetworkFeeRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.NetworkStakingInfoRequestImpl;
import io.github.manishdait.hieromirror.resource.NetworkResource;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkAddressBookRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkExchangeRateRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkFeeRequest;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkStakingInfoRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class NetworkResourceImpl implements NetworkResource {
  private final MirrorNodeClient client;

  public NetworkResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must nor be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkExchangeRateRequest exchangeRates() {
    return new NetworkExchangeRateRequestImpl(client);
  }

  @Override
  public @NonNull NetworkFeeRequest fees() {
    return new NetworkFeeRequestImpl(client);
  }

  @Override
  public @NonNull NetworkStakingInfoRequest stakingInfo() {
    return new NetworkStakingInfoRequestImpl(client);
  }

  @Override
  public @NonNull NetworkAddressBookRequest addressBook() {
    return new NetworkAddressBookRequestImpl(client);
  }
}
