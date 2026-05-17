package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;

public record NftTransfer(
    boolean isApproval,
    AccountId receiverAccountId,
    AccountId senderAccountId,
    long serialNumber,
    TokenId tokenId) {}
