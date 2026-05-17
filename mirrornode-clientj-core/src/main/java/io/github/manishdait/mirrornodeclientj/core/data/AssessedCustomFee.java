package io.github.manishdait.mirrornodeclientj.core.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import java.util.List;

public record AssessedCustomFee(
    long amount,
    AccountId collectorAccountId,
    List<AccountId> effectivePayerAccountIds,
    TokenId token_id) {}
