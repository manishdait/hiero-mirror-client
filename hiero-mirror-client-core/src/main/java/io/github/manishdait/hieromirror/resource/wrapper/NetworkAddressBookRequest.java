package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.FileId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Node;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.NetworkAddressBookQuery;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for NetworkAddressBookQuery. */
public interface NetworkAddressBookRequest
    extends QueryRequest<NetworkAddressBookQuery, Page<Node>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull NetworkAddressBookRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull NetworkAddressBookRequest order(Order order);

  /**
   * Sets the nodeId criteria filter.
   *
   * @param nodeId the {@link CriteriaParam} for nodeId
   * @return {@code this}
   */
  @NonNull NetworkAddressBookRequest nodeId(CriteriaParam<Long> nodeId);

  /**
   * Sets the fileId criteria filter.
   *
   * @param fileId the {@link CriteriaParam} for fileId
   * @return {@code this}
   */
  @NonNull NetworkAddressBookRequest fileId(CriteriaParam<FileId> fileId);
}
