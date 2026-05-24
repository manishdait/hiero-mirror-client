package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.NetworkSupply;
import io.github.manishdait.hieromirror.query.NetworkSupplyQuery;
import java.util.Optional;

public interface NetworkSupplyRequest
    extends QueryRequest<NetworkSupplyQuery, Optional<NetworkSupply>> {}
