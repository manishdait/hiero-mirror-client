package io.github.manishdait.mirrornodeclientj.core.internal.parser;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.BlockNodeApi;
import com.hedera.hashgraph.sdk.FileId;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.ScheduleId;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenSupplyType;
import com.hedera.hashgraph.sdk.TokenType;
import com.hedera.hashgraph.sdk.TopicId;
import io.github.manishdait.mirrornodeclientj.core.data.AccountBalance;
import io.github.manishdait.mirrornodeclientj.core.data.AccountInfo;
import io.github.manishdait.mirrornodeclientj.core.data.AssessedCustomFee;
import io.github.manishdait.mirrornodeclientj.core.data.Block;
import io.github.manishdait.mirrornodeclientj.core.data.BlockNodeEndpoint;
import io.github.manishdait.mirrornodeclientj.core.data.CryptoAllowance;
import io.github.manishdait.mirrornodeclientj.core.data.CustomFee;
import io.github.manishdait.mirrornodeclientj.core.data.ExchangeRate;
import io.github.manishdait.mirrornodeclientj.core.data.GeneralServiceEndpoint;
import io.github.manishdait.mirrornodeclientj.core.data.MirrorNodeEndpoint;
import io.github.manishdait.mirrornodeclientj.core.data.NetworkFee;
import io.github.manishdait.mirrornodeclientj.core.data.NetworkSupply;
import io.github.manishdait.mirrornodeclientj.core.data.Nft;
import io.github.manishdait.mirrornodeclientj.core.data.NftAllowance;
import io.github.manishdait.mirrornodeclientj.core.data.NftTransaction;
import io.github.manishdait.mirrornodeclientj.core.data.NftTransfer;
import io.github.manishdait.mirrornodeclientj.core.data.Node;
import io.github.manishdait.mirrornodeclientj.core.data.Page;
import io.github.manishdait.mirrornodeclientj.core.data.RegisteredNode;
import io.github.manishdait.mirrornodeclientj.core.data.RegisteredServiceType;
import io.github.manishdait.mirrornodeclientj.core.data.RpcRelayEndpoint;
import io.github.manishdait.mirrornodeclientj.core.data.ScheduleInfo;
import io.github.manishdait.mirrornodeclientj.core.data.ScheduleSignatures;
import io.github.manishdait.mirrornodeclientj.core.data.StakeInfo;
import io.github.manishdait.mirrornodeclientj.core.data.StakingReward;
import io.github.manishdait.mirrornodeclientj.core.data.StakingRewardTransfer;
import io.github.manishdait.mirrornodeclientj.core.data.TimestampRange;
import io.github.manishdait.mirrornodeclientj.core.data.Token;
import io.github.manishdait.mirrornodeclientj.core.data.TokenAllowance;
import io.github.manishdait.mirrornodeclientj.core.data.TokenFreezeStatus;
import io.github.manishdait.mirrornodeclientj.core.data.TokenInfo;
import io.github.manishdait.mirrornodeclientj.core.data.TokenKycStatus;
import io.github.manishdait.mirrornodeclientj.core.data.TokenPauseStatus;
import io.github.manishdait.mirrornodeclientj.core.data.TokenRelationShip;
import io.github.manishdait.mirrornodeclientj.core.data.TokenTransfer;
import io.github.manishdait.mirrornodeclientj.core.data.Topic;
import io.github.manishdait.mirrornodeclientj.core.data.TopicMessage;
import io.github.manishdait.mirrornodeclientj.core.data.Transaction;
import io.github.manishdait.mirrornodeclientj.core.data.TransactionType;
import io.github.manishdait.mirrornodeclientj.core.data.Transfer;
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
  public static Page<AccountInfo> parseAccountInfos(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("accounts").isEmpty()
        || !node.get("accounts").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode accounts = node.get("accounts").asArray();
      List<AccountInfo> accountInfos =
          accounts.valueStream().map(account -> parseAccountInfo(account).get()).toList();
      return new Page<>(accountInfos, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<AccountInfo> parseAccountInfo(JsonNode node) {
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
          new AccountInfo(
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

  public static Page<Transaction> parseTransactions(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("transactions").isEmpty()
        || !node.get("transactions").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode transactions = node.get("transactions").asArray();
      List<Transaction> transactionsList =
          transactions
              .valueStream()
              .map(transaction -> parseTransaction(transaction).get())
              .toList();

      return new Page<>(transactionsList, JsonUtils.toNullableString(node.get("links"), "next"));
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
                          JsonUtils.toEntityId(fee, "denominating_token_id", TokenId.class));
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
                          JsonUtils.toEntityId(transfer, "token_id", TokenId.class));
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      AccountId nodeAccountId = JsonUtils.toEntityId(node, "node", AccountId.class);
      long nonce = JsonUtils.toLong(node, "nonce");
      Instant parentConsensusTimestamp = JsonUtils.toInstant(node, "parent_consensus_timestamp");

      Status result = JsonUtils.toEnum(node, "result", Status.class);
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
                          JsonUtils.toLong(transfer, "amount"));
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
                          JsonUtils.toBoolean(transfer, "is_approval"));
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
                          JsonUtils.toBoolean(transfer, "is_approval"));
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
                          JsonUtils.toEntityId(fee, "token_id", TokenId.class));
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

  public static Page<CryptoAllowance> parseCryptoAllowances(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("allowances").isEmpty()
        || !node.get("allowances").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode allowances = node.get("allowances").asArray();
      List<CryptoAllowance> cryptoAllowances =
          allowances.valueStream().map(allowance -> parseCryptoAllowance(allowance).get()).toList();

      return new Page<>(cryptoAllowances, JsonUtils.toNullableString(node.get("links"), "next"));
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
        Instant to = JsonUtils.toInstant(timestamp, "to");

        timestampRange = new TimestampRange(from, to);
      }

      return Optional.of(
          new CryptoAllowance(amount, amountGranted, owner, spender, timestampRange));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<TokenAllowance> parseTokenAllowances(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("allowances").isEmpty()
        || !node.get("allowances").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode allowances = node.get("allowances").asArray();
      List<TokenAllowance> tokenAllowances =
          allowances.valueStream().map(allowance -> parseTokenAllowance(allowance).get()).toList();

      return new Page<>(tokenAllowances, JsonUtils.toNullableString(node.get("links"), "next"));
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

  public static Page<NftAllowance> parseNftAllowances(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("allowances").isEmpty()
        || !node.get("allowances").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode allowances = node.get("allowances").asArray();
      List<NftAllowance> nftAllowances =
          allowances.valueStream().map(allowance -> parseNftAllowance(allowance).get()).toList();
      return new Page<>(nftAllowances, JsonUtils.toNullableString(node.get("links"), "next"));
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

  public static Optional<TokenInfo> parseTokenInfo(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonUtils.toKey(node, "admin_key");
      AccountId autoRenewAccount =
          JsonUtils.toEntityId(node, "auto_renew_account", AccountId.class);
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

      Instant modifiedTimestamp = JsonUtils.toInstant(node, "modified_timestamp");

      String name = JsonUtils.toNullableString(node, "name");
      String memo = JsonUtils.toNullableString(node, "memo");
      Key pasueKey = JsonUtils.toKey(node, "pause_key");
      TokenPauseStatus pauseStatus = JsonUtils.toEnum(node, "pause_status", TokenPauseStatus.class);
      Key supplyKey = JsonUtils.toKey(node, "supply_key");

      TokenSupplyType supplyType = JsonUtils.toEnum(node, "supply_type", TokenSupplyType.class);

      String symbol = JsonUtils.toNullableString(node, "symbol");
      TokenId tokenId = JsonUtils.toEntityId(node, "token_id", TokenId.class);

      String totalSupply = JsonUtils.toNullableString(node, "total_supply");
      AccountId treasuryAccountId =
          JsonUtils.toEntityId(node, "treasury_account_id", AccountId.class);

      TokenType tokenType = JsonUtils.toEnum(node, "type", TokenType.class);
      Key wipeKey = JsonUtils.toKey(node, "wipe_key");

      return Optional.of(
          new TokenInfo(
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
              wipeKey));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<Token> parseTokens(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("tokens").isEmpty()
        || !node.get("tokens").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode tokens = node.get("tokens").asArray();
      List<Token> tokenList = tokens.valueStream().map(token -> parseToken(token).get()).toList();
      return new Page<>(tokenList, JsonUtils.toNullableString(node.get("links"), "next"));
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
      long decimals = JsonUtils.toLong(node, "decimals");
      String name = JsonUtils.toNullableString(node, "name");
      String symbol = JsonUtils.toNullableString(node, "symbol");
      TokenId tokenId = JsonUtils.toEntityId(node, "token_id", TokenId.class);
      TokenType tokenType = JsonUtils.toEnum(node, "type", TokenType.class);
      byte[] metadata = JsonUtils.toBytes(node, "metadata");

      return Optional.of(new Token(adminKey, decimals, name, symbol, tokenId, tokenType, metadata));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<StakingReward> parseStakingRewards(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("rewards").isEmpty()
        || !node.get("rewards").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode rewards = node.get("rewards").asArray();
      List<StakingReward> stakingRewards =
          rewards.valueStream().map(reward -> parseStakingReward(reward).get()).toList();
      return new Page<>(stakingRewards, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<StakingReward> parseStakingReward(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }
    try {
      AccountId accountId = JsonUtils.toEntityId(node, "account_id", AccountId.class);
      long amount = JsonUtils.toLong(node, "amount");
      Instant timestamp = JsonUtils.toInstant(node, "timestamp");

      return Optional.of(new StakingReward(accountId, amount, timestamp));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<TokenRelationShip> parseTokenRelationships(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("tokens").isEmpty()
        || !node.get("tokens").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode tokens = node.get("tokens").asArray();
      List<TokenRelationShip> tokenRelationShips =
          tokens.valueStream().map(token -> parseTokenRelationship(token).get()).toList();
      return new Page<>(tokenRelationShips, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<TokenRelationShip> parseTokenRelationship(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }
    try {
      boolean automaticAssociation = JsonUtils.toBoolean(node, "automatic_association");
      long balance = JsonUtils.toLong(node, "balance");
      Instant createdTimestamp = JsonUtils.toInstant(node, "created_timestamp");
      long decimals = JsonUtils.toLong(node, "decimals");
      TokenFreezeStatus freezeStatus =
          JsonUtils.toEnum(node, "freeze_status", TokenFreezeStatus.class);
      TokenKycStatus kycStatus = JsonUtils.toEnum(node, "kyc_status", TokenKycStatus.class);
      TokenId tokenId = JsonUtils.toEntityId(node, "token_id", TokenId.class);

      return Optional.of(
          new TokenRelationShip(
              automaticAssociation,
              balance,
              createdTimestamp,
              decimals,
              freezeStatus,
              kycStatus,
              tokenId));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Topic> parseTopic(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }
    try {
      Key adminKey = JsonUtils.toKey(node, "admin_key");
      Long autoRenewPeriod = JsonUtils.toNullableLong(node, "auto_renew_period");
      AccountId autoRenewAccount =
          JsonUtils.toEntityId(node, "auto_renew_account", AccountId.class);
      Instant createdTimestamp = JsonUtils.toInstant(node, "created_timestamp");
      boolean delete = JsonUtils.toBoolean(node, "deleted");
      List<Key> feeExemptKeyList = null;
      if (node.has("fee_exempt_key_list") && !node.get("fee_exempt_key_list").isNull()) {
        feeExemptKeyList =
            node.get("fee_exempt_key_list")
                .asArray()
                .valueStream()
                .map(fee -> JsonUtils.toKey(fee))
                .collect(Collectors.toUnmodifiableList());
      }

      Key feeScheduleKey = JsonUtils.toKey(node, "fee_schedule_key");
      String memo = JsonUtils.toNullableString(node, "memo");
      Key submitKey = JsonUtils.toKey(node, "submit_key");

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from = JsonUtils.toInstant(timestamp, "from");
        Instant to = JsonUtils.toInstant(timestamp, "to");

        timestampRange = new TimestampRange(from, to);
      }

      TopicId topicId = JsonUtils.toEntityId(node, "topic_id", TopicId.class);

      return Optional.of(
          new Topic(
              adminKey,
              autoRenewAccount,
              autoRenewPeriod,
              createdTimestamp,
              delete,
              feeExemptKeyList,
              feeScheduleKey,
              memo,
              submitKey,
              timestampRange,
              topicId));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<TopicMessage> parseTopicMessage(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }
    try {
      Instant consensusTimestamp = JsonUtils.toInstant(node, "consensus_timestamp");
      String message = JsonUtils.toNullableString(node, "message");
      AccountId payerAccountId = JsonUtils.toEntityId(node, "payer_account_id", AccountId.class);
      byte[] runningHash = JsonUtils.toBytes(node, "running_hash");
      int runningHashVersion = JsonUtils.toInt(node, "running_hash_version");
      long sequenceNumber = JsonUtils.toLong(node, "sequence_number");
      TopicId topicId = JsonUtils.toEntityId(node, "topic_id", TopicId.class);

      return Optional.of(
          new TopicMessage(
              consensusTimestamp,
              message,
              payerAccountId,
              runningHash,
              runningHashVersion,
              sequenceNumber,
              topicId));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<TopicMessage> parseTopicMessages(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("messages").isEmpty()
        || !node.get("messages").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode messages = node.get("messages").asArray();
      List<TopicMessage> topicMessages =
          messages.valueStream().map(message -> parseTopicMessage(message).get()).toList();
      return new Page<>(topicMessages, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<NetworkFee> parseNetworkFee(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      List<NetworkFee.Fee> fees = null;
      if (node.has("fees") && !node.get("fees").isNull()) {
        fees =
            node.get("fees")
                .asArray()
                .valueStream()
                .map(
                    fee ->
                        new NetworkFee.Fee(
                            JsonUtils.toLong(fee, "gas"),
                            JsonUtils.toNullableString(fee, "transaction_type")))
                .collect(Collectors.toUnmodifiableList());
      }

      Instant timestamp = JsonUtils.toInstant(node, "timestamp");

      return Optional.of(new NetworkFee(fees, timestamp));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<NetworkSupply> parseNetworkSupply(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      String releaseSupply = JsonUtils.toNullableString(node, "released_supply");
      String totalSupply = JsonUtils.toNullableString(node, "total_supply");
      Instant timestamp = JsonUtils.toInstant(node, "timestamp");

      return Optional.of(new NetworkSupply(releaseSupply, timestamp, totalSupply));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<ExchangeRate> parseExchangeRate(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      ExchangeRate.Rate currentRate = null;
      if (node.has("current_rate") && !node.get("current_rate").isNull()) {
        JsonNode rate = node.get("current_rate");
        currentRate =
            new ExchangeRate.Rate(
                JsonUtils.toInt(rate, "cent_equivalent"),
                JsonUtils.toLong(rate, "expiration_time"),
                JsonUtils.toInt(rate, "hbar_equivalent"));
      }

      ExchangeRate.Rate nextRate = null;
      if (node.has("next_rate") && !node.get("next_rate").isNull()) {
        JsonNode rate = node.get("next_rate");
        nextRate =
            new ExchangeRate.Rate(
                JsonUtils.toInt(rate, "cent_equivalent"),
                JsonUtils.toLong(rate, "expiration_time"),
                JsonUtils.toInt(rate, "hbar_equivalent"));
      }

      Instant timestamp = JsonUtils.toInstant(node, "timestamp");

      return Optional.of(new ExchangeRate(currentRate, nextRate, timestamp));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<StakeInfo> parseStakeInfo(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      long maxStakeRewarded = JsonUtils.toLong(node, "max_stake_rewarded");
      long maxStakingRewardRatePerHbar = JsonUtils.toLong(node, "max_staking_reward_rate_per_hbar");
      long maxTotalReward = JsonUtils.toLong(node, "max_total_reward");
      float nodeRewardFeeFraction = JsonUtils.toFloat(node, "node_reward_fee_fraction");
      long reservedStakingRewards = JsonUtils.toLong(node, "reserved_staking_rewards");
      long rewardBalanceThreshold = JsonUtils.toLong(node, "reward_balance_threshold");
      long stakeTotal = JsonUtils.toLong(node, "stake_total");

      TimestampRange stakingPeriod = null;
      if (node.has("staking_period")) {
        JsonNode timestamp = node.get("staking_period");
        Instant from = JsonUtils.toInstant(timestamp, "from");
        Instant to = JsonUtils.toInstant(timestamp, "to");

        stakingPeriod = new TimestampRange(from, to);
      }

      long stakingPeriodDuration = JsonUtils.toLong(node, "staking_period_duration");
      long stakingPeriodsStored = JsonUtils.toLong(node, "staking_periods_stored");
      float stakingRewardFeeFraction = JsonUtils.toFloat(node, "staking_reward_fee_fraction");
      long stakingRewardRate = JsonUtils.toLong(node, "staking_reward_rate");
      long stakingStartThreshold = JsonUtils.toLong(node, "staking_start_threshold");
      long unreservedStakingRewardBalance =
          JsonUtils.toLong(node, "unreserved_staking_reward_balance");

      return Optional.of(
          new StakeInfo(
              maxStakeRewarded,
              maxStakingRewardRatePerHbar,
              maxTotalReward,
              nodeRewardFeeFraction,
              reservedStakingRewards,
              rewardBalanceThreshold,
              stakeTotal,
              stakingPeriod,
              stakingPeriodDuration,
              stakingPeriodsStored,
              stakingRewardFeeFraction,
              stakingRewardRate,
              stakingStartThreshold,
              unreservedStakingRewardBalance));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<Node> parseNodes(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("nodes").isEmpty()
        || !node.get("nodes").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode nodes = node.get("nodes").asArray();
      List<Node> nodesList = nodes.valueStream().map(n -> parseNode(n).get()).toList();
      return new Page<>(nodesList, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Node> parseNode(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonUtils.toKey(node, "admin_key");

      List<Long> associatedRegisteredNodes = new ArrayList<>();
      if (node.has("associated_registered_nodes")
          && !node.get("associated_registered_nodes").isNull()) {
        associatedRegisteredNodes =
            node.get("associated_registered_nodes")
                .asArray()
                .valueStream()
                .map(n -> n.asLong())
                .collect(Collectors.toUnmodifiableList());
      }

      boolean declineReward = JsonUtils.toBoolean(node, "decline_reward");
      String description = JsonUtils.toNullableString(node, "description");
      FileId fileId = JsonUtils.toEntityId(node, "file_id", FileId.class);

      Node.ServiceEndpoint grpcProxyEndpoint = null;
      if (node.has("grpc_proxy_endpoint") && !node.get("grpc_proxy_endpoint").isNull()) {
        grpcProxyEndpoint = parseServiceEndpoint(node.get("grpc_proxy_endpoint"));
      }

      Long maxStake = JsonUtils.toNullableLong(node, "max_stake");
      String memo = JsonUtils.toNullableString(node, "memo");
      Long minStake = JsonUtils.toNullableLong(node, "min_stake");
      AccountId nodeAccountId = JsonUtils.toEntityId(node, "node_account_id", AccountId.class);
      Long nodeId = JsonUtils.toNullableLong(node, "node_id");
      String nodeCertHash = JsonUtils.toNullableString(node, "node_cert_hash");
      String publicKey = JsonUtils.toNullableString(node, "public_key");
      Long rewardRateStart = JsonUtils.toNullableLong(node, "reward_rate_start");

      List<Node.ServiceEndpoint> serviceEndpoints = new ArrayList<>();
      if (node.has("service_endpoints") && !node.get("service_endpoints").isNull()) {
        serviceEndpoints =
            node.get("service_endpoints")
                .asArray()
                .valueStream()
                .map(endpoint -> parseServiceEndpoint(endpoint))
                .collect(Collectors.toUnmodifiableList());
      }

      Long stake = JsonUtils.toNullableLong(node, "stake");
      Long stakeNotRewarded = JsonUtils.toNullableLong(node, "stake_not_rewarded");
      Long stakeRewarded = JsonUtils.toNullableLong(node, "stake_rewarded");

      TimestampRange stakingPeriod = null;
      if (node.has("staking_period")) {
        JsonNode timestamp = node.get("staking_period");
        Instant from = JsonUtils.toInstant(timestamp, "from");
        Instant to = JsonUtils.toInstant(timestamp, "to");

        stakingPeriod = new TimestampRange(from, to);
      }

      TimestampRange timestamp = null;
      if (node.has("timestamp")) {
        JsonNode timestampNode = node.get("timestamp");
        Instant from = JsonUtils.toInstant(timestampNode, "from");
        Instant to = JsonUtils.toInstant(timestampNode, "to");

        timestamp = new TimestampRange(from, to);
      }

      return Optional.of(
          new Node(
              adminKey,
              associatedRegisteredNodes,
              declineReward,
              description,
              fileId,
              grpcProxyEndpoint,
              maxStake,
              memo,
              minStake,
              nodeAccountId,
              nodeId,
              nodeCertHash,
              publicKey,
              rewardRateStart,
              serviceEndpoints,
              stake,
              stakeNotRewarded,
              stakeRewarded,
              stakingPeriod,
              timestamp));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<RegisteredNode> parseRegisteredNodes(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("registered_nodes").isEmpty()
        || !node.get("registered_nodes").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode nodes = node.get("registered_nodes").asArray();
      List<RegisteredNode> nodesList =
          nodes.valueStream().map(n -> parseRegisteredNode(n).get()).toList();
      return new Page<>(nodesList, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<RegisteredNode> parseRegisteredNode(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonUtils.toKey(node, "admin_key");
      Instant createdTimestamp = JsonUtils.toInstant(node, "created_timestamp");
      String description = JsonUtils.toNullableString(node, "description");
      long registeredNodeId = JsonUtils.toLong(node, "registered_node_id");

      List<RegisteredNode.ServiceEndpoint> serviceEndpoint = new ArrayList<>();
      if (node.has("service_endpoints") && !node.get("service_endpoints").isNull()) {
        serviceEndpoint =
            node.get("service_endpoints")
                .asArray()
                .valueStream()
                .map(endpoint -> parseRegisteredServiceEndpoint(endpoint))
                .collect(Collectors.toUnmodifiableList());
      }

      TimestampRange timestamp = null;
      if (node.has("timestamp")) {
        JsonNode timestampNode = node.get("timestamp");
        Instant from = JsonUtils.toInstant(timestampNode, "from");
        Instant to = JsonUtils.toInstant(timestampNode, "to");

        timestamp = new TimestampRange(from, to);
      }

      return Optional.of(
          new RegisteredNode(
              adminKey,
              createdTimestamp,
              description,
              registeredNodeId,
              serviceEndpoint,
              timestamp));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<Block> parseBlocks(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("blocks").isEmpty()
        || !node.get("blocks").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode blocks = node.get("blocks").asArray();
      List<Block> blockList = blocks.valueStream().map(block -> parseBlock(block).get()).toList();
      return new Page<>(blockList, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Block> parseBlock(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Long count = JsonUtils.toNullableLong(node, "count");
      Long gasUsed = JsonUtils.toNullableLong(node, "gas_used");
      String hapiVersion = JsonUtils.toNullableString(node, "hapi_version");
      String hash = JsonUtils.toNullableString(node, "hash");
      String logsBloom = JsonUtils.toNullableString(node, "logs_bloom");
      String name = JsonUtils.toNullableString(node, "name");
      Long number = JsonUtils.toNullableLong(node, "number");
      String previousHash = JsonUtils.toNullableString(node, "previous_hash");
      Long size = JsonUtils.toNullableLong(node, "size");

      TimestampRange timestamp = null;
      if (node.has("timestamp")) {
        JsonNode timestampNode = node.get("timestamp");
        Instant from = JsonUtils.toInstant(timestampNode, "from");
        Instant to = JsonUtils.toInstant(timestampNode, "to");

        timestamp = new TimestampRange(from, to);
      }

      return Optional.of(
          new Block(
              count,
              gasUsed,
              hapiVersion,
              hash,
              logsBloom,
              name,
              number,
              previousHash,
              size,
              timestamp));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  private static RegisteredNode.ServiceEndpoint parseRegisteredServiceEndpoint(JsonNode node) {
    String domainName = JsonUtils.toNullableString(node, "domain_name");
    String ipAddress = JsonUtils.toNullableString(node, "ip_address");
    int port = JsonUtils.toInt(node, "port");
    boolean requireTls = JsonUtils.toBoolean(node, "requires_tls");
    RegisteredServiceType type = JsonUtils.toEnum(node, "type", RegisteredServiceType.class);

    BlockNodeEndpoint blockNode = null;
    if (node.has("block_node") && !node.get("block_node").isNull()) {
      List<BlockNodeApi> blockNodeApis = new ArrayList<>();

      if (node.get("block_node").has("endpoint_apis")
          && !node.get("block_node").get("endpoint_apis").isNull()) {
        blockNodeApis =
            node.get("block_node")
                .get("endpoint_apis")
                .asArray()
                .valueStream()
                .map(blockApi -> BlockNodeApi.valueOf(blockApi.asString()))
                .collect(Collectors.toUnmodifiableList());
      }

      blockNode = new BlockNodeEndpoint(blockNodeApis);
    }

    GeneralServiceEndpoint generalService = null;
    if (node.has("general_service") && !node.get("general_service").isNull()) {
      String description = JsonUtils.toNullableString(node.get("general_service"), "description");
      generalService = new GeneralServiceEndpoint(description);
    }

    MirrorNodeEndpoint mirrorNode = null;
    if (node.has("mirror_node") && !node.get("mirror_node").isNull()) {
      mirrorNode = new MirrorNodeEndpoint();
    }

    RpcRelayEndpoint rpcRelay = null;
    if (node.has("rpc_relay") && !node.get("rpc_relay").isNull()) {
      rpcRelay = new RpcRelayEndpoint();
    }

    return new RegisteredNode.ServiceEndpoint(
        domainName,
        ipAddress,
        port,
        requireTls,
        type,
        blockNode,
        generalService,
        mirrorNode,
        rpcRelay);
  }

  public static Page<ScheduleInfo> parseScheduleInfos(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("schedules").isEmpty()
        || !node.get("schedules").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode schedules = node.get("schedules").asArray();
      List<ScheduleInfo> scheduleList =
          schedules.valueStream().map(schedule -> parseScheduleInfo(schedule).get()).toList();
      return new Page<>(scheduleList, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<ScheduleInfo> parseScheduleInfo(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonUtils.toKey(node, "admin_key");
      Instant consensusTimestamp = JsonUtils.toInstant(node, "consensus_timestamp");
      AccountId creatorAccountId =
          JsonUtils.toEntityId(node, "creator_account_id", AccountId.class);
      boolean deleted = JsonUtils.toBoolean(node, "deleted");
      Instant executedTimestamp = JsonUtils.toInstant(node, "executed_timestamp");
      Instant expirationTime = JsonUtils.toInstant(node, "expiration_time");
      String memo = JsonUtils.toNullableString(node, "memo");
      AccountId payerAccount = JsonUtils.toEntityId(node, "payer_account_id", AccountId.class);
      ScheduleId scheduleId = JsonUtils.toEntityId(node, "schedule_id", ScheduleId.class);

      List<ScheduleSignatures> signatures = new ArrayList<>();
      if (node.has("signatures") && !node.get("signatures").isNull()) {
        signatures =
            node.get("signatures")
                .asArray()
                .valueStream()
                .map(
                    s -> {
                      return new ScheduleSignatures(
                          JsonUtils.toInstant(s, "consensus_timestamp"),
                          JsonUtils.toBytes(s, "public_key_prefix"),
                          JsonUtils.toBytes(s, "signature"),
                          JsonUtils.toNullableString(s, "type"));
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      byte[] transactionBody = JsonUtils.toBytes(node, "transaction_body");
      boolean waitForExpiry = JsonUtils.toBoolean(node, "wait_for_expiry");

      return Optional.of(
          new ScheduleInfo(
              adminKey,
              consensusTimestamp,
              creatorAccountId,
              deleted,
              executedTimestamp,
              expirationTime,
              memo,
              payerAccount,
              scheduleId,
              signatures,
              transactionBody,
              waitForExpiry));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<Nft> parseNfts(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("nfts").isEmpty()
        || !node.get("nfts").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode nfts = node.get("nfts").asArray();
      List<Nft> nftList = nfts.valueStream().map(nft -> parseNft(nft).get()).toList();
      return new Page<>(nftList, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Nft> parseNft(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      AccountId accountId = JsonUtils.toEntityId(node, "account_id", AccountId.class);
      Instant createdTimestamp = JsonUtils.toInstant(node, "created_timestamp");
      AccountId delegatingSpender =
          JsonUtils.toEntityId(node, "delegating_spender", AccountId.class);
      boolean deleted = JsonUtils.toBoolean(node, "deleted");
      byte[] metadata = JsonUtils.toBytes(node, "metadata");
      Instant modifiedTimestamp = JsonUtils.toInstant(node, "modified_timestamp");
      long serialNumber = JsonUtils.toLong(node, "serial_number");
      AccountId spenderId = JsonUtils.toEntityId(node, "spender_id", AccountId.class);
      TokenId tokenId = JsonUtils.toEntityId(node, "token_id", TokenId.class);

      return Optional.of(
          new Nft(
              accountId,
              createdTimestamp,
              delegatingSpender,
              deleted,
              metadata,
              modifiedTimestamp,
              serialNumber,
              spenderId,
              tokenId));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Page<NftTransaction> parseNftTransactions(JsonNode node) {
    if (node == null
        || node.isEmpty()
        || node.get("transactions").isEmpty()
        || !node.get("transactions").isArray()) {
      return new Page<>(List.of(), null);
    }

    try {
      ArrayNode transactions = node.get("transactions").asArray();
      List<NftTransaction> nftsTransactions =
          transactions
              .valueStream()
              .map(transaction -> parseNftTransaction(transaction).get())
              .toList();
      return new Page<>(nftsTransactions, JsonUtils.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<NftTransaction> parseNftTransaction(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Instant consensusTimestamp = JsonUtils.toInstant(node, "consensus_timestamp");
      boolean isApproval = JsonUtils.toBoolean(node, "is_approval");
      long nonce = JsonUtils.toLong(node, "nonce");
      AccountId receiverAccountId =
          JsonUtils.toEntityId(node, "receiver_account_id", AccountId.class);
      AccountId senderAccountId = JsonUtils.toEntityId(node, "sender_account_id", AccountId.class);
      String transactionId = JsonUtils.toNullableString(node, "transaction_id");
      TransactionType type = JsonUtils.toEnum(node, "type", TransactionType.class);

      return Optional.of(
          new NftTransaction(
              consensusTimestamp,
              isApproval,
              nonce,
              receiverAccountId,
              senderAccountId,
              transactionId,
              type));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  private static Node.ServiceEndpoint parseServiceEndpoint(JsonNode node) {
    String domainName = JsonUtils.toNullableString(node, "domain_name");
    String ipAddress = JsonUtils.toNullableString(node, "ip_address_v4");
    int port = JsonUtils.toInt(node, "port");

    return new Node.ServiceEndpoint(domainName, ipAddress, port);
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
                      t -> JsonUtils.toLong(t, "balance")));
    }

    return new AccountBalance(timestamp, balance, tokens);
  }
}
