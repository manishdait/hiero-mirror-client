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

public interface AccountQueryWrapper extends QueryWrapper<AccountQuery, Optional<AccountInfo>> {
  @NonNull AccountQueryWrapper order(Order order);

  @NonNull AccountQueryWrapper limit(Integer limit);

  @NonNull AccountQueryWrapper includeTransaction(Boolean includeTransaction);

  @NonNull AccountQueryWrapper transactionType(TransactionType transactionType);

  @NonNull AccountQueryWrapper timestamp(List<CriteriaParam<Instant>> timestamp);

  @NonNull AccountCryptoAllowanceQueryWrapper cryptoAllowance();

  @NonNull AccountTokenAllowanceQueryWrapper tokenAllowance();

  @NonNull AccountNftAllowanceQueryWrapper nftAllowance();

  @NonNull AccountNftListQueryWrapper nftList();

  @NonNull AccountStakingRewardQueryWrapper stakingReward();
}
