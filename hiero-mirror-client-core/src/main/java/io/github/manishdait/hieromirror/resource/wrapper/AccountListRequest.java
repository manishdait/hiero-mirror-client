package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.model.AccountInfo;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountListQuery;
import org.jspecify.annotations.NonNull;

/** Wrapper for AccountListQuery. */
public interface AccountListRequest extends QueryRequest<AccountListQuery, Page<AccountInfo>> {
  @NonNull AccountListRequest order(Order order);

  @NonNull AccountListRequest limit(int limit);

  @NonNull AccountListRequest includeBalance(boolean includeBalance);

  @NonNull AccountListRequest publicKey(PublicKey publicKey);

  @NonNull AccountListRequest accountId(CriteriaParam<AccountId> accountId);

  @NonNull AccountListRequest balance(CriteriaParam<Hbar> balance);
}
