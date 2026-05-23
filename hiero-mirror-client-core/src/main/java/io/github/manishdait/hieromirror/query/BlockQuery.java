package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.Block;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class BlockQuery extends Query<Optional<Block>> {
  private String identifier;

  public BlockQuery() {}

  public String getHashOrNumber() {
    return identifier;
  }

  public BlockQuery setHash(final @NonNull String hash) {
    Objects.requireNonNull(hash, "hash must not be null");
    this.identifier = hash;
    return this;
  }

  public BlockQuery setNumber(final @NonNull String number) {
    Objects.requireNonNull(number, "number must not be null");
    this.identifier = number;
    return this;
  }

  @Override
  MirrorNodeRequest buildRequest(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");

    if (identifier == null) {
      throw new IllegalStateException(
          "block hash or number must be set before executing the transaction");
    }

    MirrorNodeRequest.Builder request =
        MirrorNodeRequest.newBuilder()
            .url(client.getBaseUrl() + "/api/v1/blocks/" + identifier)
            .method("GET");

    return request.build();
  }

  @Override
  Optional<Block> mapResponse(@NonNull JsonNode node) {
    return MirrorNodeJsonParser.parseBlock(node);
  }
}
