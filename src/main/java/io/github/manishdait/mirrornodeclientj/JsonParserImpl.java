package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.mirrornodeclientj.data.Account;
import io.github.manishdait.mirrornodeclientj.data.AccountBalance;
import io.github.manishdait.mirrornodeclientj.data.AssessedCustomFee;
import io.github.manishdait.mirrornodeclientj.data.CryptoAllowance;
import io.github.manishdait.mirrornodeclientj.data.CustomFee;
import io.github.manishdait.mirrornodeclientj.data.NftTransfer;
import io.github.manishdait.mirrornodeclientj.data.StakingRewardTransfer;
import io.github.manishdait.mirrornodeclientj.data.TimestampRange;
import io.github.manishdait.mirrornodeclientj.data.TokenAllowance;
import io.github.manishdait.mirrornodeclientj.data.TokenTransfer;
import io.github.manishdait.mirrornodeclientj.data.Transaction;
import io.github.manishdait.mirrornodeclientj.data.Transfer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
      throw new RuntimeException("Unable to parse json");
    }
  }

  public static Optional<Account> parseAccount(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      AccountId accountId = AccountId.fromString(node.get("account").asString());
      String alias = node.has("alias") ? node.get("alias").asString() : null;
      Long autoRenewPeriod =
          node.has("auto_renew_period") ? node.get("auto_renew_period").asLong() : null;
      AccountBalance accountBalance = null;
      if (node.has("balance")) {
        accountBalance = parseAccountBalance(node.get("balance"));
      }

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
      List<Transaction> transactions = new ArrayList<>();
      if (node.has("transactions")) {
        transactions =
            node.get("transactions")
                .asArray()
                .valueStream()
                .map(transaction -> parseTransaction(transaction).get())
                .collect(Collectors.toUnmodifiableList());
      }

      return Optional.of(
          new Account(
              accountId,
              alias,
              autoRenewPeriod,
              accountBalance,
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
              pendingReward,
              transactions));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Transaction> parseTransaction(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key batchKey =
          node.has("batch_key") && !node.get("batch_key").asString().isEmpty()
              ? parseKey(
                  node.get("batch_key").get("_type").asString(),
                  node.get("batch_key").get("key").asString())
              : null;
      byte[] bytes = node.has("bytes") ? node.get("bytes").asString().getBytes() : null;
      long chargedTxFee = node.has("charged_tx_fee") ? node.get("charged_tx_fee").asLong() : 0;
      Instant consensusTimestamp =
          node.has("consensus_timestamp") && !node.get("consensus_timestamp").asString().isEmpty()
              ? parseTimestamp(node.get("consensus_timestamp").asString())
              : null;
      AccountId entityId =
          node.has("entity_id") && !node.get("entity_id").asString().isEmpty() ? AccountId.fromString(node.get("entity_id").asString()) : null;

      List<CustomFee> maxCustomFees = new ArrayList<>();
      if (node.has("max_custom_fees")) {
        maxCustomFees =
            node.get("max_custom_fees")
                .asArray()
                .valueStream()
                .map(
                    fee -> {
                      return new CustomFee(
                          fee.has("account_id")
                              ? AccountId.fromString(fee.get("account_id").asString())
                              : null,
                          fee.has("amount") ? fee.get("amount").asLong() : 0,
                          fee.has("denominating_token_id")
                              ? TokenId.fromString(fee.get("denominating_token_id").asString())
                              : null);
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      String maxFee = node.has("max_fee") ? node.get("max_fee").asString() : null;
      byte[] memoBytes =
          node.has("memo_base64") ? node.get("memo_base64").asString().getBytes() : null;
      TransactionType name =
          node.has("name") ? TransactionType.fromValue(node.get("name").asString()) : null;

      List<NftTransfer> nftTransfers = new ArrayList<>();
      if (node.has("nft_transfers")) {
        nftTransfers =
            node.get("nft_transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new NftTransfer(
                          transfer.has("is_approval")
                              ? transfer.get("is_approval").asBoolean()
                              : false,
                          transfer.has("receiver_account_id")
                              ? AccountId.fromString(transfer.get("receiver_account_id").asString())
                              : null,
                          transfer.has("sender_account_id")
                              ? AccountId.fromString(transfer.get("sender_account_id").asString())
                              : null,
                          transfer.has("serial_number")
                              ? transfer.get("serial_number").asLong()
                              : 0,
                          transfer.has("token_id")
                              ? TokenId.fromString(transfer.get("token_id").asString())
                              : null);
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      AccountId nodeAccountId =
          node.has("node") ? AccountId.fromString(node.get("node").asString()) : null;
      long nonce = node.has("nonce") ? node.get("nonce").asLong() : 0;
      Instant parentConsensusTimestamp =
          node.has("parent_consensus_timestamp")
                  && !node.get("parent_consensus_timestamp").asString().isEmpty()
              ? parseTimestamp(node.get("parent_consensus_timestamp").asString())
              : null;
      Status result =
          node.has("result") ? Status.valueOf(node.get("result").asString().toUpperCase()) : null;
      boolean scheduled = node.has("scheduled") ? node.get("scheduled").asBoolean() : false;

      List<StakingRewardTransfer> stakingRewardTransfers = new ArrayList<>();
      if (node.has("staking_reward_transfers")) {
        stakingRewardTransfers =
            node.get("staking_reward_transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new StakingRewardTransfer(
                          transfer.has("account")
                              ? AccountId.fromString(transfer.get("account").asString())
                              : null,
                          transfer.has("amount") ? transfer.get("amount").asLong() : 0);
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      List<TokenTransfer> tokenTransfers = new ArrayList<>();
      if (node.has("token_transfers")) {
        tokenTransfers =
            node.get("token_transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new TokenTransfer(
                          transfer.has("token_id")
                              ? TokenId.fromString(transfer.get("token_id").asString())
                              : null,
                          transfer.has("account")
                              ? AccountId.fromString(transfer.get("account").asString())
                              : null,
                          transfer.has("amount") ? transfer.get("amount").asLong() : 0,
                          transfer.has("is_approval")
                              ? transfer.get("is_approval").asBoolean()
                              : false);
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      byte[] transactionHash =
          node.has("transaction_hash") ? node.get("transaction_hash").asString().getBytes() : null;
      String transactionId =
          node.has("transaction_id") ? node.get("transaction_id").asString() : null;

      List<Transfer> transfers = new ArrayList<>();
      if (node.has("transfers")) {
        transfers =
            node.get("transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new Transfer(
                          transfer.has("account")
                              ? AccountId.fromString(transfer.get("account").asString())
                              : null,
                          transfer.has("amount") ? transfer.get("amount").asLong() : 0,
                          transfer.has("is_approval")
                              ? transfer.get("is_approval").asBoolean()
                              : false);
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      String validDurationSecond =
          node.has("valid_duration_seconds") ? node.get("valid_duration_seconds").asString() : null;
      Instant validStartTimestamp =
          node.has("valid_start_timestamp")
                  && !node.get("valid_start_timestamp").asString().isEmpty()
              ? parseTimestamp(node.get("valid_start_timestamp").asString())
              : null;

      List<AssessedCustomFee> assessedCustomFees = new ArrayList<>();
      if (node.has("assessed_custom_fees")) {
        assessedCustomFees =
            node.get("assessed_custom_fees")
                .asArray()
                .valueStream()
                .map(
                    fee -> {
                      return new AssessedCustomFee(
                          fee.has("amount") ? fee.get("amount").asLong() : 0,
                          fee.has("collector_account_id")
                              ? AccountId.fromString(fee.get("collector_account_id").asString())
                              : null,
                          fee.has("effective_payer_account_ids")
                              ? fee.get("effective_payer_account_ids")
                                  .asArray()
                                  .valueStream()
                                  .map(
                                      a ->
                                          a.asString() != null
                                              ? AccountId.fromString(a.asString())
                                              : null)
                                  .collect(Collectors.toUnmodifiableList())
                              : List.of(),
                          fee.has("token_id")
                              ? TokenId.fromString(fee.get("token_id").asString())
                              : null);
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      return Optional.of(
          new Transaction(
              batchKey,
              bytes,
              chargedTxFee,
              consensusTimestamp,
              entityId,
              maxCustomFees,
              maxFee,
              memoBytes,
              name,
              nftTransfers,
              nodeAccountId,
              nonce,
              parentConsensusTimestamp,
              result,
              scheduled,
              stakingRewardTransfers,
              tokenTransfers,
              transactionHash,
              transactionId,
              transfers,
              validDurationSecond,
              validStartTimestamp,
              assessedCustomFees));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static List<CryptoAllowance> parseCryptoAllowances(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("allowances").isEmpty()
        || !node.get("allowances").isArray()) {
      return List.of();
    }

    try {
      ArrayNode allowances = node.get("allowances").asArray();
      return allowances
          .valueStream()
          .map(allowance -> parseCryptoAllowance(allowance).get())
          .toList();
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json");
    }
  }

  public static Optional<CryptoAllowance> parseCryptoAllowance(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      long amount = node.has("amount") ? node.get("amount").asLong() : 0;
      long amountGranted = node.has("amount_granted") ? node.get("amount_granted").asLong() : 0;
      AccountId owner =
          node.has("owner") ? AccountId.fromString(node.get("owner").asString()) : null;
      AccountId spender =
          node.has("spender") ? AccountId.fromString(node.get("spender").asString()) : null;

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from =
            node.has("from") && !node.get("from").asString().isEmpty()
                ? parseTimestamp(node.get("from").asString())
                : null;
        Instant to =
            node.has("to") && !node.get("to").asString().isEmpty()
                ? parseTimestamp(node.get("to").asString())
                : null;

        timestampRange = new TimestampRange(from, to);
      }

      return Optional.of(
          new CryptoAllowance(amount, amountGranted, owner, spender, timestampRange));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json");
    }
  }

  public static List<TokenAllowance> parseTokenAllowances(JsonNode node) {
    if (node == null
      || node.isEmpty()
      || node.get("allowances").isEmpty()
      || !node.get("allowances").isArray()) {
      return List.of();
    }

    try {
      ArrayNode allowances = node.get("allowances").asArray();
      return allowances
        .valueStream()
        .map(allowance -> parseTokenAllowance(allowance).get())
        .toList();
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json");
    }
  }

  public static Optional<TokenAllowance> parseTokenAllowance(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      long amount = node.has("amount") ? node.get("amount").asLong() : 0;
      long amountGranted = node.has("amount_granted") ? node.get("amount_granted").asLong() : 0;
      AccountId owner =
        node.has("owner") ? AccountId.fromString(node.get("owner").asString()) : null;
      AccountId spender =
        node.has("spender") ? AccountId.fromString(node.get("spender").asString()) : null;

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from =
          node.has("from") && !node.get("from").asString().isEmpty()
            ? parseTimestamp(node.get("from").asString())
            : null;
        Instant to =
          node.has("to") && !node.get("to").asString().isEmpty()
            ? parseTimestamp(node.get("to").asString())
            : null;

        timestampRange = new TimestampRange(from, to);
      }

      TokenId tokenId = node.has("token_id")? TokenId.fromString(node.get("token_id").asString()) : null;

      return Optional.of(
        new TokenAllowance(amount, amountGranted, owner, spender, timestampRange, tokenId));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json");
    }
  }

  private static AccountBalance parseAccountBalance(JsonNode node) {
    Instant timestamp =
        node.has("timestamp") ? parseTimestamp(node.get("timestamp").asString()) : null;
    long balance = node.has("balance") ? node.get("balance").asLong() : 0;
    Map<TokenId, Long> tokens = new HashMap<>();
    if (node.has("tokens")) {
      tokens =
          node.get("tokens")
              .asArray()
              .valueStream()
              .collect(
                  Collectors.toUnmodifiableMap(
                      t -> TokenId.fromString(t.get("token_id").asString()),
                      t -> t.get("balance").asLong()));
    }

    return new AccountBalance(timestamp, balance, tokens);
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
