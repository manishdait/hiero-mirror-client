package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.RegisteredNode;
import io.github.manishdait.hieromirror.model.RegisteredServiceType;
import io.github.manishdait.hieromirror.query.NetworkRegisteredAddressBookQuery;
import org.jspecify.annotations.NonNull;

public interface NetworkRegisteredAddressBookRequest
    extends QueryRequest<NetworkRegisteredAddressBookQuery, Page<RegisteredNode>> {
  @NonNull NetworkRegisteredAddressBookRequest limit(int limit);

  @NonNull NetworkRegisteredAddressBookRequest order(Order order);

  @NonNull NetworkRegisteredAddressBookRequest registeredNodeId(CriteriaParam<Long> nodeId);

  @NonNull NetworkRegisteredAddressBookRequest type(RegisteredServiceType type);
}
