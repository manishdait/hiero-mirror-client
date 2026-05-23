package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;

public record TransactionTransfer(AccountId account, long amount, boolean isApproval) {}
