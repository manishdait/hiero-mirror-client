package io.github.manishdait.hieromirror.query;

import com.hedera.hashgraph.sdk.NftId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class NftByIdQuery extends Query<Optional<Nft>> {
  private final NftId nftId;

  public NftByIdQuery(MirrorNodeClient client, NftId nftId) {
    super(client);
    this.nftId = nftId;
  }

  public NftId getNftId() {
    return nftId;
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
  Optional<Nft> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseNft(node);
  }
}
