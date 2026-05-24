package io.github.manishdait.hieromirror.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeJsonParser;
import io.github.manishdait.hieromirror.internal.core.MirrorNodeRequest;
import io.github.manishdait.hieromirror.model.Block;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

/** Query to get block by hash or number. */
public final class BlockQuery extends Query<Optional<Block>> {
  private String identifier;

  /** Constructor. */
  public BlockQuery() {}

  /**
   * Gets the identifier `hashOrNumber` of the block.
   *
   * @return the hash or number of block
   */
  public String getHashOrNumber() {
    return identifier;
  }

  /**
   * Sets the hash of the block.
   *
   * @param hash the hash of block
   * @return {@code this}
   */
  public BlockQuery setHash(final @NonNull String hash) {
    Objects.requireNonNull(hash, "hash must not be null");
    this.identifier = hash;
    return this;
  }

  /**
   * Sets the number of the block.
   *
   * @param number the number of block
   * @return {@code this}
   */
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
