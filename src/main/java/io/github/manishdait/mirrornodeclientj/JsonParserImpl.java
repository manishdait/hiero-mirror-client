package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenSupplyType;
import com.hedera.hashgraph.sdk.TokenType;
import io.github.manishdait.mirrornodeclientj.data.Account;
import io.github.manishdait.mirrornodeclientj.data.AccountBalance;
import io.github.manishdait.mirrornodeclientj.data.AssessedCustomFee;
import io.github.manishdait.mirrornodeclientj.data.CryptoAllowance;
import io.github.manishdait.mirrornodeclientj.data.CustomFee;
import io.github.manishdait.mirrornodeclientj.data.NftAllowance;
import io.github.manishdait.mirrornodeclientj.data.NftTransfer;
import io.github.manishdait.mirrornodeclientj.data.StakingRewardTransfer;
import io.github.manishdait.mirrornodeclientj.data.TimestampRange;
import io.github.manishdait.mirrornodeclientj.data.Token;
import io.github.manishdait.mirrornodeclientj.data.TokenAllowance;
import io.github.manishdait.mirrornodeclientj.data.TokenMetadata;
import io.github.manishdait.mirrornodeclientj.data.TokenPauseStatus;
import io.github.manishdait.mirrornodeclientj.data.TokenTransfer;
import io.github.manishdait.mirrornodeclientj.data.Transaction;
import io.github.manishdait.mirrornodeclientj.data.Transfer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
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
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Account> parseAccount(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      AccountId accountId = JsonUtils.toEntityId(node, "account", AccountId.class);
      String alias = JsonUtils.toNullableString(node, "alias");
      Long autoRenewPeriod = JsonUtils.toNullableLong(node, "auto_renew_period");

      AccountBalance accountBalance = null;
      if (node.has("balance")) {
        accountBalance = parseAccountBalance(node.get("balance"));
      }

      Instant createdTimestamp = JsonUtils.toInstant(node, "created_timestamp");
      boolean declineReward = JsonUtils.toBoolean(node, "decline_reward");
      boolean deleted = JsonUtils.toBoolean(node, "deleted");
      Long ethereumNonce = JsonUtils.toNullableLong(node, "ethereum_nonce");
      String evmAddress = JsonUtils.toNullableString(node, "evm_address");
      Instant expiryTimestamp = JsonUtils.toInstant(node, "expiry_timestamp");
      Key key = JsonUtils.toKey(node, "key");
      int maxAutomaticTokenAssociations = JsonUtils.toInt(node, "max_automatic_token_associations");
      String memo = JsonUtils.toNullableString(node, "memo");
      boolean requiredReceiverSignature = JsonUtils.toBoolean(node, "receiver_sig_required");
      AccountId stakedAccountId = JsonUtils.toEntityId(node, "staked_account_id", AccountId.class);
      Long stakedNodeId = JsonUtils.toNullableLong(node, "staked_node_id");
      Instant stakePeriodStart = JsonUtils.toInstant(node, "stake_period_start");

      long pendingReward = JsonUtils.toLong(node, "pending_reward");
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
      Key batchKey = JsonUtils.toKey(node, "batch_key");
      byte[] bytes = JsonUtils.toBytes(node, "bytes");
      long chargedTxFee = JsonUtils.toLong(node, "charged_tx_fee");
      Instant consensusTimestamp = JsonUtils.toInstant(node, "consensus_timestamp");
      AccountId entityId = JsonUtils.toEntityId(node, "entity_id", AccountId.class);

      List<CustomFee> maxCustomFees = new ArrayList<>();
      if (node.has("max_custom_fees")) {
        maxCustomFees =
            node.get("max_custom_fees")
                .asArray()
                .valueStream()
                .map(
                    fee -> {
                      return new CustomFee(
                          JsonUtils.toEntityId(fee, "account_id", AccountId.class),
                          JsonUtils.toLong(fee, "amount"),
                          JsonUtils.toEntityId(fee, "denominating_token_id", TokenId.class)
                      );
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      String maxFee = JsonUtils.toNullableString(node, "max_fee");
      byte[] memoBytes = JsonUtils.toBytes(node, "memo_base64");

      TransactionType name = JsonUtils.toEnum(node, "name", TransactionType.class);

      List<NftTransfer> nftTransfers = new ArrayList<>();
      if (node.has("nft_transfers")) {
        nftTransfers =
            node.get("nft_transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new NftTransfer(
                          JsonUtils.toBoolean(transfer, "is_approval"),
                          JsonUtils.toEntityId(transfer, "receiver_account_id", AccountId.class),
                          JsonUtils.toEntityId(transfer, "sender_account_id", AccountId.class),
                          JsonUtils.toLong(transfer, "serial_number"),
                          JsonUtils.toEntityId(transfer, "token_id", TokenId.class)
                      );
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      AccountId nodeAccountId = JsonUtils.toEntityId(node, "node", AccountId.class);
      long nonce = JsonUtils.toLong(node, "nonce");
      Instant parentConsensusTimestamp = JsonUtils.toInstant(node, "parent_consensus_timestamp");

      Status result = JsonUtils.toEnum(node, "result",Status.class);
      boolean scheduled = JsonUtils.toBoolean(node, "scheduled");

      List<StakingRewardTransfer> stakingRewardTransfers = new ArrayList<>();
      if (node.has("staking_reward_transfers")) {
        stakingRewardTransfers =
            node.get("staking_reward_transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new StakingRewardTransfer(
                          JsonUtils.toEntityId(transfer, "account", AccountId.class),
                          JsonUtils.toLong(transfer, "amount")
                      );
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
                          JsonUtils.toEntityId(transfer, "token_id", TokenId.class),
                          JsonUtils.toEntityId(transfer, "account", AccountId.class),
                          JsonUtils.toLong(transfer, "amount"),
                          JsonUtils.toBoolean(transfer, "is_approval")
                      );
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      byte[] transactionHash = JsonUtils.toBytes(node, "transaction_hash");
      String transactionId = JsonUtils.toNullableString(node, "transaction_id");

      List<Transfer> transfers = new ArrayList<>();
      if (node.has("transfers")) {
        transfers =
            node.get("transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new Transfer(
                          JsonUtils.toEntityId(transfer, "account", AccountId.class),
                          JsonUtils.toLong(transfer, "amount"),
                          JsonUtils.toBoolean(transfer, "is_approval")
                      );
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      String validDurationSecond = JsonUtils.toNullableString(node, "valid_duration_seconds");
      Instant validStartTimestamp = JsonUtils.toInstant(node, "valid_start_timestamp");

      List<AssessedCustomFee> assessedCustomFees = new ArrayList<>();
      if (node.has("assessed_custom_fees")) {
        assessedCustomFees =
            node.get("assessed_custom_fees")
                .asArray()
                .valueStream()
                .map(
                    fee -> {
                      return new AssessedCustomFee(
                          JsonUtils.toLong(fee, "amount"),
                          JsonUtils.toEntityId(fee, "collector_account_id", AccountId.class),
                          fee.has("effective_payer_account_ids")
                              ? fee.get("effective_payer_account_ids")
                                  .asArray()
                                  .valueStream()
                                  .map(
                                      a ->
                                          !a.asString().isEmpty()
                                              ? AccountId.fromString(a.asString())
                                              : null)
                                  .collect(Collectors.toUnmodifiableList())
                              : List.of(),
                          JsonUtils.toEntityId(fee, "token_id", TokenId.class)
                      );
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
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<CryptoAllowance> parseCryptoAllowance(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      long amount = JsonUtils.toLong(node, "amount");
      long amountGranted = JsonUtils.toLong(node, "amount_granted");
      AccountId owner = JsonUtils.toEntityId(node, "owner", AccountId.class);
      AccountId spender = JsonUtils.toEntityId(node, "spender", AccountId.class);

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from = JsonUtils.toInstant(timestamp, "from");
        Instant to =  JsonUtils.toInstant(timestamp, "to");

        timestampRange = new TimestampRange(from, to);
      }

      return Optional.of(
          new CryptoAllowance(amount, amountGranted, owner, spender, timestampRange));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
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
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<TokenAllowance> parseTokenAllowance(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      long amount = JsonUtils.toLong(node, "amount");
      long amountGranted = JsonUtils.toLong(node, "amount_granted");
      AccountId owner = JsonUtils.toEntityId(node, "owner", AccountId.class);
      AccountId spender = JsonUtils.toEntityId(node, "spender", AccountId.class);

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from = JsonUtils.toInstant(timestamp, "from");
        Instant to = JsonUtils.toInstant(timestamp, "to");

        timestampRange = new TimestampRange(from, to);
      }

      TokenId tokenId = JsonUtils.toEntityId(node, "token_id", TokenId.class);

      return Optional.of(
          new TokenAllowance(amount, amountGranted, owner, spender, timestampRange, tokenId));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static List<NftAllowance> parseNftAllowances(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("allowances").isEmpty()
        || !node.get("allowances").isArray()) {
      return List.of();
    }

    try {
      ArrayNode allowances = node.get("allowances").asArray();
      return allowances.valueStream().map(allowance -> parseNftAllowance(allowance).get()).toList();
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<NftAllowance> parseNftAllowance(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      boolean approvedForAll = JsonUtils.toBoolean(node, "approved_for_all");
      AccountId owner = JsonUtils.toEntityId(node, "owner", AccountId.class);

      AccountId payerAccountId = JsonUtils.toEntityId(node, "payer_account_id", AccountId.class);
      AccountId spender = JsonUtils.toEntityId(node, "spender", AccountId.class);

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from = JsonUtils.toInstant(timestamp, "from");
        Instant to = JsonUtils.toInstant(timestamp, "to");

        timestampRange = new TimestampRange(from, to);
      }

      TokenId tokenId = JsonUtils.toEntityId(node, "token_id", TokenId.class);

      return Optional.of(
          new NftAllowance(
              approvedForAll, owner, payerAccountId, spender, timestampRange, tokenId));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Token> parseToken(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonUtils.toKey(node, "admin_key");
      AccountId autoRenewAccount = JsonUtils.toEntityId(node, "auto_renew_account", AccountId.class);
      Long autoRenewPeriod = JsonUtils.toNullableLong(node, "auto_renew_period");
      Instant createdTimestamp = JsonUtils.toInstant(node, "created_timestamp");

      String decimals = JsonUtils.toNullableString(node, "decimals");
      boolean deleted = JsonUtils.toBoolean(node, "deleted");
      Long expiryTimestamp = JsonUtils.toNullableLong(node, "expiry_timestamp");

      Key feeScheduleKey = JsonUtils.toKey(node, "fee_schedule_key");
      boolean freezeDefault = JsonUtils.toBoolean(node, "freeze_default");

      Key freezeKey = JsonUtils.toKey(node, "freeze_key");

      String initialSupply = JsonUtils.toNullableString(node, "initial_supply");
      Key kycKey = JsonUtils.toKey(node, "kyc_key");

      String maxSupply = JsonUtils.toNullableString(node, "max_supply");
      byte[] metadata = JsonUtils.toBytes(node, "metadata");
      Key metadataKey = JsonUtils.toKey(node, "metadata_key");

      Instant modifiedTimestamp= JsonUtils.toInstant(node, "modified_timestamp");

      String name = JsonUtils.toNullableString(node, "name");
      String memo = JsonUtils.toNullableString(node, "memo");
      Key pasueKey = JsonUtils.toKey(node, "pause_key");
      TokenPauseStatus pauseStatus = JsonUtils.toEnum(node, "pause_status", TokenPauseStatus.class);
      Key supplyKey = JsonUtils.toKey(node, "supply_key");

      TokenSupplyType supplyType = JsonUtils.toEnum(node, "supply_type", TokenSupplyType.class);

      String symbol = JsonUtils.toNullableString(node, "symbol");
      TokenId tokenId = JsonUtils.toEntityId(node, "token_id", TokenId.class);

      String totalSupply = JsonUtils.toNullableString(node, "total_supply");
      AccountId treasuryAccountId = JsonUtils.toEntityId(node, "treasury_account_id", AccountId.class);

      TokenType tokenType = JsonUtils.toEnum(node, "type", TokenType.class);
      Key wipeKey = JsonUtils.toKey(node, "wipe_key");

      return Optional.of(
        new Token(
          adminKey,
          autoRenewAccount,
          autoRenewPeriod,
          createdTimestamp,
          decimals,
          deleted,
          expiryTimestamp,
          feeScheduleKey,
          freezeDefault,
          freezeKey,
          initialSupply,
          kycKey,
          maxSupply,
          metadata,
          metadataKey,
          modifiedTimestamp,
          name,
          memo,
          pasueKey,
          pauseStatus,
          supplyKey,
          supplyType,
          symbol,
          tokenId,
          totalSupply,
          treasuryAccountId,
          tokenType,
          wipeKey
        )
      );
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static List<TokenMetadata> parseTokenMetas(JsonNode node) {
    if (node == null
      || node.isEmpty()
      || node.get("tokens").isEmpty()
      || !node.get("tokens").isArray()) {
      return List.of();
    }

    try {
      ArrayNode tokens = node.get("tokens").asArray();
      return tokens.valueStream().map(token -> parseTokenMeta(token).get()).toList();
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<TokenMetadata> parseTokenMeta(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonUtils.toKey(node, "admin_key");
      long decimals = JsonUtils.toLong(node, "decimals");
      String name = JsonUtils.toNullableString(node, "name");
      String symbol = JsonUtils.toNullableString(node, "symbol");
      TokenId tokenId = JsonUtils.toEntityId(node, "token_id", TokenId.class);
      TokenType tokenType = JsonUtils.toEnum(node, "type", TokenType.class);
      byte[] metadata = JsonUtils.toBytes(node, "metadata");

      return Optional.of(
        new TokenMetadata(
          adminKey,
          decimals,
          name,
          symbol,
          tokenId,
          tokenType,
          metadata
        )
      );
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  private static AccountBalance parseAccountBalance(JsonNode node) {
    Instant timestamp = JsonUtils.toInstant(node, "timestamp");
    long balance = JsonUtils.toLong(node, "balance");

    Map<TokenId, Long> tokens = new HashMap<>();
    if (node.has("tokens")) {
      tokens =
          node.get("tokens")
              .asArray()
              .valueStream()
              .collect(
                  Collectors.toUnmodifiableMap(
                      t -> JsonUtils.toEntityId(t, "token_id", TokenId.class),
                      t -> JsonUtils.toLong(t, "balance")
                  )
              );
    }

    return new AccountBalance(timestamp, balance, tokens);
  }
}
