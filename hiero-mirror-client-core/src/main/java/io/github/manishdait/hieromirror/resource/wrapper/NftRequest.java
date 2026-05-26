package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.query.NftQuery;
import java.util.Optional;

/** Request Wrapper for NftQuery. */
public interface NftRequest extends QueryRequest<NftQuery, Optional<Nft>> {}
