package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.query.BlockQuery;
import java.util.Optional;

public interface BlockRequest extends QueryRequest<BlockQuery, Optional<Block>> {}
