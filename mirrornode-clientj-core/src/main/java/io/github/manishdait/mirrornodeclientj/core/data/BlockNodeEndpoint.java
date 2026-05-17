package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.BlockNodeApi;
import java.util.List;

public record BlockNodeEndpoint(List<BlockNodeApi> blockNodeApis) {}
