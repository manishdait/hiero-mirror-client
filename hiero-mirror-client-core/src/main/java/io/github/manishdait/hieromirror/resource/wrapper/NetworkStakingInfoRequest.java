package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.StakeInfo;
import io.github.manishdait.hieromirror.query.NetworkStakingInfoQuery;
import java.util.Optional;

/** Request Wrapper for NetworkStakingInfoQuery. */
public interface NetworkStakingInfoRequest
    extends QueryRequest<NetworkStakingInfoQuery, Optional<StakeInfo>> {}
