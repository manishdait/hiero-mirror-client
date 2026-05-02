package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;

public record NftTransfer(
    boolean isApproval,
    AccountId receiverAccountId,
    AccountId senderAccountId,
    long serialNumber,
    TokenId tokenId) {}
