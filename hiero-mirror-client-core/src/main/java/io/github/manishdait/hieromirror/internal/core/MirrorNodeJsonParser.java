package io.github.manishdait.hieromirror.internal.core;

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
import io.github.manishdait.hieromirror.model.AccountBalance;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.AssessedCustomFee;
import io.github.manishdait.hieromirror.model.Block;
import io.github.manishdait.hieromirror.model.BlockNodeEndpoint;
import io.github.manishdait.hieromirror.model.CryptoAllowance;
import io.github.manishdait.hieromirror.model.CustomFee;
import io.github.manishdait.hieromirror.model.ExchangeRate;
import io.github.manishdait.hieromirror.model.GeneralServiceEndpoint;
import io.github.manishdait.hieromirror.model.MirrorNodeEndpoint;
import io.github.manishdait.hieromirror.model.NetworkFee;
import io.github.manishdait.hieromirror.model.NetworkSupply;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.NftAllowance;
import io.github.manishdait.hieromirror.model.NftTransaction;
import io.github.manishdait.hieromirror.model.NftTransfer;
import io.github.manishdait.hieromirror.model.Node;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.RegisteredNode;
import io.github.manishdait.hieromirror.model.RegisteredServiceType;
import io.github.manishdait.hieromirror.model.RpcRelayEndpoint;
import io.github.manishdait.hieromirror.model.ScheduleInfo;
import io.github.manishdait.hieromirror.model.ScheduleSignatures;
import io.github.manishdait.hieromirror.model.StakeInfo;
import io.github.manishdait.hieromirror.model.StakingReward;
import io.github.manishdait.hieromirror.model.StakingRewardTransfer;
import io.github.manishdait.hieromirror.model.TimestampRange;
import io.github.manishdait.hieromirror.model.Token;
import io.github.manishdait.hieromirror.model.TokenAllowance;
import io.github.manishdait.hieromirror.model.TokenFreezeStatus;
import io.github.manishdait.hieromirror.model.TokenInfo;
import io.github.manishdait.hieromirror.model.TokenKycStatus;
import io.github.manishdait.hieromirror.model.TokenPauseStatus;
import io.github.manishdait.hieromirror.model.TokenRelationShip;
import io.github.manishdait.hieromirror.model.TokenTransfer;
import io.github.manishdait.hieromirror.model.Topic;
import io.github.manishdait.hieromirror.model.TopicMessage;
import io.github.manishdait.hieromirror.model.Transaction;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.model.Transfer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;

public final class MirrorNodeJsonParser {
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
      return new Page<>(accountInfos, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<AccountInfo> parseAccountInfo(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      AccountId accountId = JsonHelper.toEntityId(node, "account", AccountId.class);
      String alias = JsonHelper.toNullableString(node, "alias");
      Long autoRenewPeriod = JsonHelper.toNullableLong(node, "auto_renew_period");

      AccountBalance accountBalance = null;
      if (node.has("balance")) {
        accountBalance = parseAccountBalance(node.get("balance"));
      }

      Instant createdTimestamp = JsonHelper.toInstant(node, "created_timestamp");
      boolean declineReward = JsonHelper.toBoolean(node, "decline_reward");
      boolean deleted = JsonHelper.toBoolean(node, "deleted");
      Long ethereumNonce = JsonHelper.toNullableLong(node, "ethereum_nonce");
      String evmAddress = JsonHelper.toNullableString(node, "evm_address");
      Instant expiryTimestamp = JsonHelper.toInstant(node, "expiry_timestamp");
      Key key = JsonHelper.toKey(node, "key");
      int maxAutomaticTokenAssociations =
          JsonHelper.toInt(node, "max_automatic_token_associations");
      String memo = JsonHelper.toNullableString(node, "memo");
      boolean requiredReceiverSignature = JsonHelper.toBoolean(node, "receiver_sig_required");
      AccountId stakedAccountId = JsonHelper.toEntityId(node, "staked_account_id", AccountId.class);
      Long stakedNodeId = JsonHelper.toNullableLong(node, "staked_node_id");
      Instant stakePeriodStart = JsonHelper.toInstant(node, "stake_period_start");

      long pendingReward = JsonHelper.toLong(node, "pending_reward");
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

      return new Page<>(transactionsList, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Transaction> parseTransaction(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key batchKey = JsonHelper.toKey(node, "batch_key");
      byte[] bytes = JsonHelper.toBytes(node, "bytes");
      long chargedTxFee = JsonHelper.toLong(node, "charged_tx_fee");
      Instant consensusTimestamp = JsonHelper.toInstant(node, "consensus_timestamp");
      AccountId entityId = JsonHelper.toEntityId(node, "entity_id", AccountId.class);

      List<CustomFee> maxCustomFees = new ArrayList<>();
      if (node.has("max_custom_fees")) {
        maxCustomFees =
            node.get("max_custom_fees")
                .asArray()
                .valueStream()
                .map(
                    fee -> {
                      return new CustomFee(
                          JsonHelper.toEntityId(fee, "account_id", AccountId.class),
                          JsonHelper.toLong(fee, "amount"),
                          JsonHelper.toEntityId(fee, "denominating_token_id", TokenId.class));
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      String maxFee = JsonHelper.toNullableString(node, "max_fee");
      byte[] memoBytes = JsonHelper.toBytes(node, "memo_base64");

      TransactionType name = JsonHelper.toEnum(node, "name", TransactionType.class);

      List<NftTransfer> nftTransfers = new ArrayList<>();
      if (node.has("nft_transfers")) {
        nftTransfers =
            node.get("nft_transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new NftTransfer(
                          JsonHelper.toBoolean(transfer, "is_approval"),
                          JsonHelper.toEntityId(transfer, "receiver_account_id", AccountId.class),
                          JsonHelper.toEntityId(transfer, "sender_account_id", AccountId.class),
                          JsonHelper.toLong(transfer, "serial_number"),
                          JsonHelper.toEntityId(transfer, "token_id", TokenId.class));
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      AccountId nodeAccountId = JsonHelper.toEntityId(node, "node", AccountId.class);
      long nonce = JsonHelper.toLong(node, "nonce");
      Instant parentConsensusTimestamp = JsonHelper.toInstant(node, "parent_consensus_timestamp");

      Status result = JsonHelper.toEnum(node, "result", Status.class);
      boolean scheduled = JsonHelper.toBoolean(node, "scheduled");

      List<StakingRewardTransfer> stakingRewardTransfers = new ArrayList<>();
      if (node.has("staking_reward_transfers")) {
        stakingRewardTransfers =
            node.get("staking_reward_transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new StakingRewardTransfer(
                          JsonHelper.toEntityId(transfer, "account", AccountId.class),
                          JsonHelper.toLong(transfer, "amount"));
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
                          JsonHelper.toEntityId(transfer, "token_id", TokenId.class),
                          JsonHelper.toEntityId(transfer, "account", AccountId.class),
                          JsonHelper.toLong(transfer, "amount"),
                          JsonHelper.toBoolean(transfer, "is_approval"));
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      byte[] transactionHash = JsonHelper.toBytes(node, "transaction_hash");
      String transactionId = JsonHelper.toNullableString(node, "transaction_id");

      List<Transfer> transfers = new ArrayList<>();
      if (node.has("transfers")) {
        transfers =
            node.get("transfers")
                .asArray()
                .valueStream()
                .map(
                    transfer -> {
                      return new Transfer(
                          JsonHelper.toEntityId(transfer, "account", AccountId.class),
                          JsonHelper.toLong(transfer, "amount"),
                          JsonHelper.toBoolean(transfer, "is_approval"));
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      String validDurationSecond = JsonHelper.toNullableString(node, "valid_duration_seconds");
      Instant validStartTimestamp = JsonHelper.toInstant(node, "valid_start_timestamp");

      List<AssessedCustomFee> assessedCustomFees = new ArrayList<>();
      if (node.has("assessed_custom_fees")) {
        assessedCustomFees =
            node.get("assessed_custom_fees")
                .asArray()
                .valueStream()
                .map(
                    fee -> {
                      return new AssessedCustomFee(
                          JsonHelper.toLong(fee, "amount"),
                          JsonHelper.toEntityId(fee, "collector_account_id", AccountId.class),
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
                          JsonHelper.toEntityId(fee, "token_id", TokenId.class));
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

      return new Page<>(cryptoAllowances, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<CryptoAllowance> parseCryptoAllowance(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      long amount = JsonHelper.toLong(node, "amount");
      long amountGranted = JsonHelper.toLong(node, "amount_granted");
      AccountId owner = JsonHelper.toEntityId(node, "owner", AccountId.class);
      AccountId spender = JsonHelper.toEntityId(node, "spender", AccountId.class);

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from = JsonHelper.toInstant(timestamp, "from");
        Instant to = JsonHelper.toInstant(timestamp, "to");

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

      return new Page<>(tokenAllowances, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<TokenAllowance> parseTokenAllowance(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      long amount = JsonHelper.toLong(node, "amount");
      long amountGranted = JsonHelper.toLong(node, "amount_granted");
      AccountId owner = JsonHelper.toEntityId(node, "owner", AccountId.class);
      AccountId spender = JsonHelper.toEntityId(node, "spender", AccountId.class);

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from = JsonHelper.toInstant(timestamp, "from");
        Instant to = JsonHelper.toInstant(timestamp, "to");

        timestampRange = new TimestampRange(from, to);
      }

      TokenId tokenId = JsonHelper.toEntityId(node, "token_id", TokenId.class);

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
      return new Page<>(nftAllowances, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<NftAllowance> parseNftAllowance(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      boolean approvedForAll = JsonHelper.toBoolean(node, "approved_for_all");
      AccountId owner = JsonHelper.toEntityId(node, "owner", AccountId.class);

      AccountId payerAccountId = JsonHelper.toEntityId(node, "payer_account_id", AccountId.class);
      AccountId spender = JsonHelper.toEntityId(node, "spender", AccountId.class);

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from = JsonHelper.toInstant(timestamp, "from");
        Instant to = JsonHelper.toInstant(timestamp, "to");

        timestampRange = new TimestampRange(from, to);
      }

      TokenId tokenId = JsonHelper.toEntityId(node, "token_id", TokenId.class);

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
      Key adminKey = JsonHelper.toKey(node, "admin_key");
      AccountId autoRenewAccount =
          JsonHelper.toEntityId(node, "auto_renew_account", AccountId.class);
      Long autoRenewPeriod = JsonHelper.toNullableLong(node, "auto_renew_period");
      Instant createdTimestamp = JsonHelper.toInstant(node, "created_timestamp");

      String decimals = JsonHelper.toNullableString(node, "decimals");
      boolean deleted = JsonHelper.toBoolean(node, "deleted");
      Long expiryTimestamp = JsonHelper.toNullableLong(node, "expiry_timestamp");

      Key feeScheduleKey = JsonHelper.toKey(node, "fee_schedule_key");
      boolean freezeDefault = JsonHelper.toBoolean(node, "freeze_default");

      Key freezeKey = JsonHelper.toKey(node, "freeze_key");

      String initialSupply = JsonHelper.toNullableString(node, "initial_supply");
      Key kycKey = JsonHelper.toKey(node, "kyc_key");

      String maxSupply = JsonHelper.toNullableString(node, "max_supply");
      byte[] metadata = JsonHelper.toBytes(node, "metadata");
      Key metadataKey = JsonHelper.toKey(node, "metadata_key");

      Instant modifiedTimestamp = JsonHelper.toInstant(node, "modified_timestamp");

      String name = JsonHelper.toNullableString(node, "name");
      String memo = JsonHelper.toNullableString(node, "memo");
      Key pasueKey = JsonHelper.toKey(node, "pause_key");
      TokenPauseStatus pauseStatus =
          JsonHelper.toEnum(node, "pause_status", TokenPauseStatus.class);
      Key supplyKey = JsonHelper.toKey(node, "supply_key");

      TokenSupplyType supplyType = JsonHelper.toEnum(node, "supply_type", TokenSupplyType.class);

      String symbol = JsonHelper.toNullableString(node, "symbol");
      TokenId tokenId = JsonHelper.toEntityId(node, "token_id", TokenId.class);

      String totalSupply = JsonHelper.toNullableString(node, "total_supply");
      AccountId treasuryAccountId =
          JsonHelper.toEntityId(node, "treasury_account_id", AccountId.class);

      TokenType tokenType = JsonHelper.toEnum(node, "type", TokenType.class);
      Key wipeKey = JsonHelper.toKey(node, "wipe_key");

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
      return new Page<>(tokenList, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Token> parseToken(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonHelper.toKey(node, "admin_key");
      long decimals = JsonHelper.toLong(node, "decimals");
      String name = JsonHelper.toNullableString(node, "name");
      String symbol = JsonHelper.toNullableString(node, "symbol");
      TokenId tokenId = JsonHelper.toEntityId(node, "token_id", TokenId.class);
      TokenType tokenType = JsonHelper.toEnum(node, "type", TokenType.class);
      byte[] metadata = JsonHelper.toBytes(node, "metadata");

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
      return new Page<>(stakingRewards, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<StakingReward> parseStakingReward(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }
    try {
      AccountId accountId = JsonHelper.toEntityId(node, "account_id", AccountId.class);
      long amount = JsonHelper.toLong(node, "amount");
      Instant timestamp = JsonHelper.toInstant(node, "timestamp");

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
      return new Page<>(tokenRelationShips, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<TokenRelationShip> parseTokenRelationship(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }
    try {
      boolean automaticAssociation = JsonHelper.toBoolean(node, "automatic_association");
      long balance = JsonHelper.toLong(node, "balance");
      Instant createdTimestamp = JsonHelper.toInstant(node, "created_timestamp");
      long decimals = JsonHelper.toLong(node, "decimals");
      TokenFreezeStatus freezeStatus =
          JsonHelper.toEnum(node, "freeze_status", TokenFreezeStatus.class);
      TokenKycStatus kycStatus = JsonHelper.toEnum(node, "kyc_status", TokenKycStatus.class);
      TokenId tokenId = JsonHelper.toEntityId(node, "token_id", TokenId.class);

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
      Key adminKey = JsonHelper.toKey(node, "admin_key");
      Long autoRenewPeriod = JsonHelper.toNullableLong(node, "auto_renew_period");
      AccountId autoRenewAccount =
          JsonHelper.toEntityId(node, "auto_renew_account", AccountId.class);
      Instant createdTimestamp = JsonHelper.toInstant(node, "created_timestamp");
      boolean delete = JsonHelper.toBoolean(node, "deleted");
      List<Key> feeExemptKeyList = null;
      if (node.has("fee_exempt_key_list") && !node.get("fee_exempt_key_list").isNull()) {
        feeExemptKeyList =
            node.get("fee_exempt_key_list")
                .asArray()
                .valueStream()
                .map(fee -> JsonHelper.toKey(fee))
                .collect(Collectors.toUnmodifiableList());
      }

      Key feeScheduleKey = JsonHelper.toKey(node, "fee_schedule_key");
      String memo = JsonHelper.toNullableString(node, "memo");
      Key submitKey = JsonHelper.toKey(node, "submit_key");

      TimestampRange timestampRange = null;
      if (node.has("timestamp")) {
        JsonNode timestamp = node.get("timestamp");
        Instant from = JsonHelper.toInstant(timestamp, "from");
        Instant to = JsonHelper.toInstant(timestamp, "to");

        timestampRange = new TimestampRange(from, to);
      }

      TopicId topicId = JsonHelper.toEntityId(node, "topic_id", TopicId.class);

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
      Instant consensusTimestamp = JsonHelper.toInstant(node, "consensus_timestamp");
      String message = JsonHelper.toNullableString(node, "message");
      AccountId payerAccountId = JsonHelper.toEntityId(node, "payer_account_id", AccountId.class);
      byte[] runningHash = JsonHelper.toBytes(node, "running_hash");
      int runningHashVersion = JsonHelper.toInt(node, "running_hash_version");
      long sequenceNumber = JsonHelper.toLong(node, "sequence_number");
      TopicId topicId = JsonHelper.toEntityId(node, "topic_id", TopicId.class);

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
      return new Page<>(topicMessages, JsonHelper.toNullableString(node.get("links"), "next"));
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
                            JsonHelper.toLong(fee, "gas"),
                            JsonHelper.toNullableString(fee, "transaction_type")))
                .collect(Collectors.toUnmodifiableList());
      }

      Instant timestamp = JsonHelper.toInstant(node, "timestamp");

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
      String releaseSupply = JsonHelper.toNullableString(node, "released_supply");
      String totalSupply = JsonHelper.toNullableString(node, "total_supply");
      Instant timestamp = JsonHelper.toInstant(node, "timestamp");

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
                JsonHelper.toInt(rate, "cent_equivalent"),
                JsonHelper.toLong(rate, "expiration_time"),
                JsonHelper.toInt(rate, "hbar_equivalent"));
      }

      ExchangeRate.Rate nextRate = null;
      if (node.has("next_rate") && !node.get("next_rate").isNull()) {
        JsonNode rate = node.get("next_rate");
        nextRate =
            new ExchangeRate.Rate(
                JsonHelper.toInt(rate, "cent_equivalent"),
                JsonHelper.toLong(rate, "expiration_time"),
                JsonHelper.toInt(rate, "hbar_equivalent"));
      }

      Instant timestamp = JsonHelper.toInstant(node, "timestamp");

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
      long maxStakeRewarded = JsonHelper.toLong(node, "max_stake_rewarded");
      long maxStakingRewardRatePerHbar =
          JsonHelper.toLong(node, "max_staking_reward_rate_per_hbar");
      long maxTotalReward = JsonHelper.toLong(node, "max_total_reward");
      float nodeRewardFeeFraction = JsonHelper.toFloat(node, "node_reward_fee_fraction");
      long reservedStakingRewards = JsonHelper.toLong(node, "reserved_staking_rewards");
      long rewardBalanceThreshold = JsonHelper.toLong(node, "reward_balance_threshold");
      long stakeTotal = JsonHelper.toLong(node, "stake_total");

      TimestampRange stakingPeriod = null;
      if (node.has("staking_period")) {
        JsonNode timestamp = node.get("staking_period");
        Instant from = JsonHelper.toInstant(timestamp, "from");
        Instant to = JsonHelper.toInstant(timestamp, "to");

        stakingPeriod = new TimestampRange(from, to);
      }

      long stakingPeriodDuration = JsonHelper.toLong(node, "staking_period_duration");
      long stakingPeriodsStored = JsonHelper.toLong(node, "staking_periods_stored");
      float stakingRewardFeeFraction = JsonHelper.toFloat(node, "staking_reward_fee_fraction");
      long stakingRewardRate = JsonHelper.toLong(node, "staking_reward_rate");
      long stakingStartThreshold = JsonHelper.toLong(node, "staking_start_threshold");
      long unreservedStakingRewardBalance =
          JsonHelper.toLong(node, "unreserved_staking_reward_balance");

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
      return new Page<>(nodesList, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Node> parseNode(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonHelper.toKey(node, "admin_key");

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

      boolean declineReward = JsonHelper.toBoolean(node, "decline_reward");
      String description = JsonHelper.toNullableString(node, "description");
      FileId fileId = JsonHelper.toEntityId(node, "file_id", FileId.class);

      Node.ServiceEndpoint grpcProxyEndpoint = null;
      if (node.has("grpc_proxy_endpoint") && !node.get("grpc_proxy_endpoint").isNull()) {
        grpcProxyEndpoint = parseServiceEndpoint(node.get("grpc_proxy_endpoint"));
      }

      Long maxStake = JsonHelper.toNullableLong(node, "max_stake");
      String memo = JsonHelper.toNullableString(node, "memo");
      Long minStake = JsonHelper.toNullableLong(node, "min_stake");
      AccountId nodeAccountId = JsonHelper.toEntityId(node, "node_account_id", AccountId.class);
      Long nodeId = JsonHelper.toNullableLong(node, "node_id");
      String nodeCertHash = JsonHelper.toNullableString(node, "node_cert_hash");
      String publicKey = JsonHelper.toNullableString(node, "public_key");
      Long rewardRateStart = JsonHelper.toNullableLong(node, "reward_rate_start");

      List<Node.ServiceEndpoint> serviceEndpoints = new ArrayList<>();
      if (node.has("service_endpoints") && !node.get("service_endpoints").isNull()) {
        serviceEndpoints =
            node.get("service_endpoints")
                .asArray()
                .valueStream()
                .map(endpoint -> parseServiceEndpoint(endpoint))
                .collect(Collectors.toUnmodifiableList());
      }

      Long stake = JsonHelper.toNullableLong(node, "stake");
      Long stakeNotRewarded = JsonHelper.toNullableLong(node, "stake_not_rewarded");
      Long stakeRewarded = JsonHelper.toNullableLong(node, "stake_rewarded");

      TimestampRange stakingPeriod = null;
      if (node.has("staking_period")) {
        JsonNode timestamp = node.get("staking_period");
        Instant from = JsonHelper.toInstant(timestamp, "from");
        Instant to = JsonHelper.toInstant(timestamp, "to");

        stakingPeriod = new TimestampRange(from, to);
      }

      TimestampRange timestamp = null;
      if (node.has("timestamp")) {
        JsonNode timestampNode = node.get("timestamp");
        Instant from = JsonHelper.toInstant(timestampNode, "from");
        Instant to = JsonHelper.toInstant(timestampNode, "to");

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
      return new Page<>(nodesList, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<RegisteredNode> parseRegisteredNode(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonHelper.toKey(node, "admin_key");
      Instant createdTimestamp = JsonHelper.toInstant(node, "created_timestamp");
      String description = JsonHelper.toNullableString(node, "description");
      long registeredNodeId = JsonHelper.toLong(node, "registered_node_id");

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
        Instant from = JsonHelper.toInstant(timestampNode, "from");
        Instant to = JsonHelper.toInstant(timestampNode, "to");

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
      return new Page<>(blockList, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Block> parseBlock(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Long count = JsonHelper.toNullableLong(node, "count");
      Long gasUsed = JsonHelper.toNullableLong(node, "gas_used");
      String hapiVersion = JsonHelper.toNullableString(node, "hapi_version");
      String hash = JsonHelper.toNullableString(node, "hash");
      String logsBloom = JsonHelper.toNullableString(node, "logs_bloom");
      String name = JsonHelper.toNullableString(node, "name");
      Long number = JsonHelper.toNullableLong(node, "number");
      String previousHash = JsonHelper.toNullableString(node, "previous_hash");
      Long size = JsonHelper.toNullableLong(node, "size");

      TimestampRange timestamp = null;
      if (node.has("timestamp")) {
        JsonNode timestampNode = node.get("timestamp");
        Instant from = JsonHelper.toInstant(timestampNode, "from");
        Instant to = JsonHelper.toInstant(timestampNode, "to");

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
    String domainName = JsonHelper.toNullableString(node, "domain_name");
    String ipAddress = JsonHelper.toNullableString(node, "ip_address");
    int port = JsonHelper.toInt(node, "port");
    boolean requireTls = JsonHelper.toBoolean(node, "requires_tls");
    RegisteredServiceType type = JsonHelper.toEnum(node, "type", RegisteredServiceType.class);

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
      String description = JsonHelper.toNullableString(node.get("general_service"), "description");
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
      return new Page<>(scheduleList, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<ScheduleInfo> parseScheduleInfo(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Key adminKey = JsonHelper.toKey(node, "admin_key");
      Instant consensusTimestamp = JsonHelper.toInstant(node, "consensus_timestamp");
      AccountId creatorAccountId =
          JsonHelper.toEntityId(node, "creator_account_id", AccountId.class);
      boolean deleted = JsonHelper.toBoolean(node, "deleted");
      Instant executedTimestamp = JsonHelper.toInstant(node, "executed_timestamp");
      Instant expirationTime = JsonHelper.toInstant(node, "expiration_time");
      String memo = JsonHelper.toNullableString(node, "memo");
      AccountId payerAccount = JsonHelper.toEntityId(node, "payer_account_id", AccountId.class);
      ScheduleId scheduleId = JsonHelper.toEntityId(node, "schedule_id", ScheduleId.class);

      List<ScheduleSignatures> signatures = new ArrayList<>();
      if (node.has("signatures") && !node.get("signatures").isNull()) {
        signatures =
            node.get("signatures")
                .asArray()
                .valueStream()
                .map(
                    s -> {
                      return new ScheduleSignatures(
                          JsonHelper.toInstant(s, "consensus_timestamp"),
                          JsonHelper.toBytes(s, "public_key_prefix"),
                          JsonHelper.toBytes(s, "signature"),
                          JsonHelper.toNullableString(s, "type"));
                    })
                .collect(Collectors.toUnmodifiableList());
      }

      byte[] transactionBody = JsonHelper.toBytes(node, "transaction_body");
      boolean waitForExpiry = JsonHelper.toBoolean(node, "wait_for_expiry");

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
      return new Page<>(nftList, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<Nft> parseNft(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      AccountId accountId = JsonHelper.toEntityId(node, "account_id", AccountId.class);
      Instant createdTimestamp = JsonHelper.toInstant(node, "created_timestamp");
      AccountId delegatingSpender =
          JsonHelper.toEntityId(node, "delegating_spender", AccountId.class);
      boolean deleted = JsonHelper.toBoolean(node, "deleted");
      byte[] metadata = JsonHelper.toBytes(node, "metadata");
      Instant modifiedTimestamp = JsonHelper.toInstant(node, "modified_timestamp");
      long serialNumber = JsonHelper.toLong(node, "serial_number");
      AccountId spenderId = JsonHelper.toEntityId(node, "spender_id", AccountId.class);
      TokenId tokenId = JsonHelper.toEntityId(node, "token_id", TokenId.class);

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
      return new Page<>(nftsTransactions, JsonHelper.toNullableString(node.get("links"), "next"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to parse json", e);
    }
  }

  public static Optional<NftTransaction> parseNftTransaction(JsonNode node) {
    if (node == null || node.isEmpty()) {
      return Optional.empty();
    }

    try {
      Instant consensusTimestamp = JsonHelper.toInstant(node, "consensus_timestamp");
      boolean isApproval = JsonHelper.toBoolean(node, "is_approval");
      long nonce = JsonHelper.toLong(node, "nonce");
      AccountId receiverAccountId =
          JsonHelper.toEntityId(node, "receiver_account_id", AccountId.class);
      AccountId senderAccountId = JsonHelper.toEntityId(node, "sender_account_id", AccountId.class);
      String transactionId = JsonHelper.toNullableString(node, "transaction_id");
      TransactionType type = JsonHelper.toEnum(node, "type", TransactionType.class);

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
    String domainName = JsonHelper.toNullableString(node, "domain_name");
    String ipAddress = JsonHelper.toNullableString(node, "ip_address_v4");
    int port = JsonHelper.toInt(node, "port");

    return new Node.ServiceEndpoint(domainName, ipAddress, port);
  }

  private static AccountBalance parseAccountBalance(JsonNode node) {
    Instant timestamp = JsonHelper.toInstant(node, "timestamp");
    long balance = JsonHelper.toLong(node, "balance");

    Map<TokenId, Long> tokens = new HashMap<>();
    if (node.has("tokens")) {
      tokens =
          node.get("tokens")
              .asArray()
              .valueStream()
              .collect(
                  Collectors.toUnmodifiableMap(
                      t -> JsonHelper.toEntityId(t, "token_id", TokenId.class),
                      t -> JsonHelper.toLong(t, "balance")));
    }

    return new AccountBalance(timestamp, balance, tokens);
  }
}
