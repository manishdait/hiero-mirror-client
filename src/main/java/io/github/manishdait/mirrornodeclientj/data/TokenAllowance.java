package io.github.manishdait.mirrornodeclientj.data;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;

public record TokenAllowance(
  long amount,
  long amountGranted,
  AccountId owner,
  AccountId spender,
  TimestampRange timestamp,
  TokenId tokenId
) {
}
