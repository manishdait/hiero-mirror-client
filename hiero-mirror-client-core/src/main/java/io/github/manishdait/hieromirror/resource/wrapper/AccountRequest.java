package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.TransactionType;
import io.github.manishdait.hieromirror.query.AccountQuery;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public interface AccountRequest extends QueryRequest<AccountQuery, Optional<AccountInfo>> {
  @NonNull AccountRequest order(Order order);

  @NonNull AccountRequest limit(Integer limit);

  @NonNull AccountRequest includeTransaction(Boolean includeTransaction);

  @NonNull AccountRequest transactionType(TransactionType transactionType);

  @NonNull AccountRequest timestamp(List<CriteriaParam<Instant>> timestamp);

  @NonNull AccountCryptoAllowanceRequest cryptoAllowance();

  @NonNull AccountTokenAllowanceRequest tokenAllowance();

  @NonNull AccountNftAllowanceRequest nftAllowance();

  @NonNull AccountNftListRequest nftList();

  @NonNull AccountStakingRewardRequest stakingReward();
}
