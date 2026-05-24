package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.RegisteredNode;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.NetworkRegisteredAddressBookQuery;

public interface NetworkRegisteredNodeRequest
    extends QueryRequest<NetworkRegisteredAddressBookQuery, Page<RegisteredNode>> {}
