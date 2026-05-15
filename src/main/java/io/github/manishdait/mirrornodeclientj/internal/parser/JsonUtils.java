package io.github.manishdait.mirrornodeclientj.internal.parser;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenSupplyType;
import com.hedera.hashgraph.sdk.TokenType;
import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.data.TokenFreezeStatus;
import io.github.manishdait.mirrornodeclientj.data.TokenKycStatus;
import io.github.manishdait.mirrornodeclientj.data.TokenPauseStatus;
import io.github.manishdait.mirrornodeclientj.data.TransactionType;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.JsonNode;

public class JsonUtils {
  public static Key toKey(final @NonNull JsonNode node, final @NonNull String label) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    if (!node.has(label) || node.get(label).isNull()) {
      return null;
    }

    JsonNode keyNode = node.get(label);
    return toKey(keyNode);
  }

  public static Key toKey(JsonNode keyNode) {
    String keyType = keyNode.get("_type").asString();
    String keyHex = keyNode.get("key").asString();

    return switch (keyType) {
      case "ED25519" -> PublicKey.fromString(keyHex);

      case "ECDSA_SECP256K1" -> PublicKey.fromStringECDSA(keyHex);

      case "ProtobufEncoded" -> {
        byte[] decodedBytes = HexFormat.of().parseHex(keyHex);
        try {
          yield Key.fromBytes(decodedBytes);
        } catch (Exception e) {
          throw new IllegalArgumentException("Invalid Protobuf encoding", e);
        }
      }

      default -> throw new UnsupportedOperationException("Unknown key type: " + keyType);
    };
  }

  public static Instant toInstant(final @NonNull JsonNode node, final @NonNull String label) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    if (!node.has(label) || node.get(label).isNull()) {
      return null;
    }

    String timestamp = node.get(label).asString();

    if (timestamp.isEmpty()) {
      return null;
    }

    String[] parts = timestamp.split("\\.");

    long seconds = Long.parseLong(parts[0]);
    long nanos = 0;

    if (parts.length > 1) {
      String nanoString = parts[1];
      nanoString = String.format("%-9s", nanoString).replace(' ', '0');
      nanos = Long.parseLong(nanoString);
    }

    return Instant.ofEpochSecond(seconds, nanos);
  }

  public static String toNullableString(final @NonNull JsonNode node, final @NonNull String label) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    return node.has(label) && !node.get(label).isNull() ? node.get(label).asString() : null;
  }

  public static byte[] toBytes(final @NonNull JsonNode node, final @NonNull String label) {
    String str = toNullableString(node, label);
    return str == null ? null : str.getBytes();
  }

  public static boolean toBoolean(final @NonNull JsonNode node, final @NonNull String label) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    return node.has(label) && !node.get(label).isNull() ? node.get(label).asBoolean() : false;
  }

  public static Long toNullableLong(final @NonNull JsonNode node, final @NonNull String label) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    return node.has(label) && !node.get(label).isNull() ? node.get(label).asLong() : null;
  }

  public static long toLong(final @NonNull JsonNode node, final @NonNull String label) {
    Long result = toNullableLong(node, label);
    return result == null ? 0 : result;
  }

  public static int toInt(final @NonNull JsonNode node, final @NonNull String label) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    return node.has(label) && !node.get(label).isNull() ? node.get(label).asInt() : 0;
  }

  public static float toFloat(final @NonNull JsonNode node, final @NonNull String label) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    return node.has(label) && !node.get(label).isNull() ? node.get(label).asFloat() : 0.0f;
  }

  public static <T> T toEntityId(
      final @NonNull JsonNode node, final @NonNull String label, final @NonNull Class<T> clazz) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    if (!node.has(label) || node.get(label).isNull()) {
      return null;
    }

    String entityId = node.get(label).asString();
    if (entityId.isEmpty()) {
      return null;
    }

    return switch (clazz.getSimpleName()) {
      case "AccountId" -> clazz.cast(AccountId.fromString(entityId));
      case "TokenId" -> clazz.cast(TokenId.fromString(entityId));
      case "TopicId" -> clazz.cast(TopicId.fromString(entityId));
      default -> throw new RuntimeException("Unsupported class");
    };
  }

  public static <T> T toEnum(
      final @NonNull JsonNode node, final @NonNull String label, final @NonNull Class<T> clazz) {
    Objects.requireNonNull(node, "node must not be null");
    Objects.requireNonNull(label, "label must not be null");

    if (!node.has(label) || node.get(label).isNull()) {
      return null;
    }

    String enumStr = node.get(label).asString().toUpperCase();
    if (enumStr.isEmpty()) {
      return null;
    }

    return switch (clazz.getSimpleName()) {
      case "TransactionType" -> clazz.cast(TransactionType.fromValue(enumStr));
      case "Status" -> clazz.cast(Status.valueOf(enumStr));
      case "TokenPauseStatus" -> clazz.cast(TokenPauseStatus.fromString(enumStr));
      case "TokenType" -> clazz.cast(TokenType.valueOf(enumStr));
      case "TokenSupplyType" -> clazz.cast(TokenSupplyType.valueOf(enumStr));
      case "TokenKycStatus" -> clazz.cast(TokenKycStatus.fromString(enumStr));
      case "TokenFreezeStatus" -> clazz.cast(TokenFreezeStatus.fromString(enumStr));
      default -> throw new RuntimeException("Unsupported class");
    };
  }
}
