package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.BlockNodeApi;
import java.util.List;

public record BlockNodeEndpoint(List<BlockNodeApi> blockNodeApis) {}
