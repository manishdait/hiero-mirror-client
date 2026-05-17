package io.github.manishdait.mirrornodeclientj.core.query;

import com.hedera.hashgraph.sdk.NftId;
import io.github.manishdait.mirrornodeclientj.core.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.core.data.Nft;
import io.github.manishdait.mirrornodeclientj.core.internal.core.MirrorNodeRequest;
import io.github.manishdait.mirrornodeclientj.core.internal.parser.JsonParserImpl;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

public class NftByIdQuery extends Query<Optional<Nft>> {
  private final NftId nftId;

  public NftByIdQuery(MirrorNodeClient client, NftId nftId) {
    super(client);
    this.nftId = nftId;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(
                this.client.getBaseUrl()
                    + "/api/v1/tokens/"
                    + nftId.tokenId
                    + "/nfts/"
                    + nftId.serial)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<Nft> mapResponse(JsonNode node) {
    return JsonParserImpl.parseNft(node);
  }
}
