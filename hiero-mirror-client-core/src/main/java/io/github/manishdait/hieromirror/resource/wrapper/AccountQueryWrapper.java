package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.TransactionType;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AccountQueryWrapper {
  AccountQueryWrapper order(Order order);

  AccountQueryWrapper limit(Integer limit);

  AccountQueryWrapper includeTransaction(Boolean includeTransaction);

  AccountQueryWrapper transactionType(TransactionType transactionType);

  AccountQueryWrapper timestamps(List<CriteriaParam<Instant>> timestamps);

  Optional<AccountInfo> call();
}
