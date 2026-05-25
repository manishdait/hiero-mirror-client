package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.RegisteredNode;
import io.github.manishdait.hieromirror.model.RegisteredServiceType;
import io.github.manishdait.hieromirror.query.NetworkRegisteredAddressBookQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for NetworkRegisteredAddressBookQuery. */
public interface NetworkRegisteredAddressBookRequest
    extends QueryRequest<NetworkRegisteredAddressBookQuery, Page<RegisteredNode>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull NetworkRegisteredAddressBookRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull NetworkRegisteredAddressBookRequest order(Order order);

  /**
   * Sets the nodeId criteria filter.
   *
   * @param nodeId the {@link CriteriaParam} for nodeId
   * @return {@code this}
   */
  @NonNull NetworkRegisteredAddressBookRequest registeredNodeId(CriteriaParam<Long> nodeId);

  /**
   * Sets the registered service type filter.
   *
   * @param type the {@link RegisteredServiceType}
   * @return {@code this}
   */
  @NonNull NetworkRegisteredAddressBookRequest type(RegisteredServiceType type);
}
