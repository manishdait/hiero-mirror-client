package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.FileId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Node;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.NetworkAddressBookQuery;
import org.jspecify.annotations.NonNull;

public interface NetworkAddressBookRequest
    extends QueryRequest<NetworkAddressBookQuery, Page<Node>> {
  @NonNull NetworkAddressBookRequest limit(int limit);

  @NonNull NetworkAddressBookRequest order(Order order);

  @NonNull NetworkAddressBookRequest nodeId(CriteriaParam<Long> nodeId);

  @NonNull NetworkAddressBookRequest fileId(CriteriaParam<FileId> fileId);
}
