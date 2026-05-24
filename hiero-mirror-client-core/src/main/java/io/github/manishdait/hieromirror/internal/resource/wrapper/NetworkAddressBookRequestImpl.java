package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.FileId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Node;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.NetworkAddressBookQuery;
import io.github.manishdait.hieromirror.resource.wrapper.NetworkAddressBookRequest;
import java.time.Duration;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class NetworkAddressBookRequestImpl implements NetworkAddressBookRequest {
  private final MirrorNodeClient client;

  public NetworkAddressBookRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull NetworkAddressBookRequest limit(int limit) {
    return null;
  }

  @Override
  public @NonNull NetworkAddressBookRequest order(Order order) {
    return null;
  }

  @Override
  public @NonNull NetworkAddressBookRequest nodeId(CriteriaParam<Long> nodeId) {
    return null;
  }

  @Override
  public @NonNull NetworkAddressBookRequest fileId(CriteriaParam<FileId> fileId) {
    return null;
  }

  @Override
  public @NonNull NetworkAddressBookQuery getQuery() {
    return null;
  }

  @Override
  public @NonNull Page<Node> call() {
    return null;
  }

  @Override
  public @NonNull Page<Node> call(@NonNull Duration timeout) {
    return null;
  }
}
