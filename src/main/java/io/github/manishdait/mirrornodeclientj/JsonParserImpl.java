package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.mirrornodeclientj.data.Account;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;

public class JsonParserImpl {
  public static List<Account> parseAccounts(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("accounts").isEmpty()
        || !node.get("accounts").isArray()) {
      return List.of();
    }

    try {
      ArrayNode accounts = node.get("accounts").asArray();
      return accounts.valueStream().map(account -> parseAccount(account).get()).toList();
    } catch (Exception e) {
      System.out.println(node);
      e.printStackTrace();
      throw new RuntimeException("Unable to parse json");
    }
  }

  private static Optional<Account> parseAccount(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      AccountId accountId = AccountId.fromString(node.get("account").asString());
      String alias = node.has("alias") ? node.get("alias").asString() : null;
      Long autoRenewPeriod =
          node.has("auto_renew_period") ? node.get("auto_renew_period").asLong() : null;
      Instant createdTimestamp = parseTimestamp(node.get("created_timestamp").asString());
      boolean declineReward = node.get("decline_reward").asBoolean();
      boolean deleted = node.get("deleted").asBoolean();
      Long ethereumNonce = node.has("ethereum_nonce") ? node.get("ethereum_nonce").asLong() : null;
      String evmAddress = node.get("evm_address").asString();
      Instant expiryTimestamp = parseTimestamp(node.get("expiry_timestamp").asString());
      Key key =
          parseKey(node.get("key").get("_type").asString(), node.get("key").get("key").asString());
      int maxAutomaticTokenAssociations = node.get("max_automatic_token_associations").asInt();
      String memo = node.get("memo").asString();
      boolean requiredReceiverSignature = node.get("receiver_sig_required").asBoolean();
      AccountId stakedAccountId =
          node.has("staked_account_id") && !node.get("staked_account_id").asString().isEmpty()
              ? AccountId.fromString(node.get("staked_account_id").asString())
              : null;
      Long stakedNodeId =
          node.has("staked_node_id") && !node.get("staked_node_id").asString().isEmpty()
              ? node.get("staked_node_id").asLong()
              : null;
      Instant stakePeriodStart =
          node.has("stake_period_start") && !node.get("stake_period_start").asString().isEmpty()
              ? parseTimestamp(node.get("stake_period_start").asString())
              : null;
      long pendingReward = Long.parseLong(node.get("pending_reward").asString());

      return Optional.of(
          new Account(
              accountId,
              alias,
              autoRenewPeriod,
              createdTimestamp,
              declineReward,
              deleted,
              ethereumNonce,
              evmAddress,
              expiryTimestamp,
              key,
              maxAutomaticTokenAssociations,
              memo,
              requiredReceiverSignature,
              stakedAccountId,
              stakedNodeId,
              stakePeriodStart,
              pendingReward));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json");
    }
  }

  private static Instant parseTimestamp(String timestamp) {
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

  private static Key parseKey(String keyType, String keyHex) {
    Key parsedKey;

    switch (keyType) {
      case "ED25519":
        parsedKey = PublicKey.fromString(keyHex);
        break;

      case "ECDSA_SECP256K1":
        parsedKey = PublicKey.fromStringECDSA(keyHex);
        break;

      case "ProtobufEncoded":
        byte[] decodedBytes = HexFormat.of().parseHex(keyHex);
        try {
          parsedKey = Key.fromBytes(decodedBytes);
        } catch (Exception e) {
          throw new IllegalArgumentException("Invalid Protobuf encoding", e);
        }
        break;

      default:
        throw new UnsupportedOperationException("Unknown key type: " + keyType);
    }

    return parsedKey;
  }
}
