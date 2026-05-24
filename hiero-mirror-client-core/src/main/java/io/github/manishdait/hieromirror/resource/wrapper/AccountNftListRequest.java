package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountNftListQuery;
import org.jspecify.annotations.NonNull;

public interface AccountNftListRequest extends QueryRequest<AccountNftListQuery, Page<Nft>> {
  @NonNull AccountNftListRequest limit(int limit);

  @NonNull AccountNftListRequest order(Order order);

  @NonNull AccountNftListRequest senderId(CriteriaParam<AccountId> spenderId);

  @NonNull AccountNftListRequest tokenId(CriteriaParam<TokenId> tokenId);

  @NonNull AccountNftListRequest serialNumber(CriteriaParam<Long> tokenId);
}
