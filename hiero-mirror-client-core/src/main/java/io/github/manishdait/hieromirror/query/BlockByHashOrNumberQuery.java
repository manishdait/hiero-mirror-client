package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.internal.parser.JsonParserImpl;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class BlockByHashOrNumberQuery extends Query<Optional<Block>> {
  public final String hashOrNumber;

  public BlockByHashOrNumberQuery(MirrorNodeClient client, String hashOrNumber) {
    super(client);
    this.hashOrNumber = hashOrNumber;
  }

  public String getHashOrNumber() {
    return hashOrNumber;
  }

  @Override
  MirrorNodeRequest buildRequest() {
    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(this.client.getBaseUrl() + "/api/v1/blocks/" + hashOrNumber)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<Block> mapResponse(@NonNull JsonNode node) {
    return JsonParserImpl.parseBlock(node);
  }
}
