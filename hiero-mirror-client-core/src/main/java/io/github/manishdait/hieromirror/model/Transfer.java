package io.github.manishdait.hieromirror.model;

import com.hedera.hashgraph.sdk.AccountId;

public record Transfer(AccountId account, long amount, boolean isApproval) {}
