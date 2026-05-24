package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Nft;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.query.AccountNftListQuery;
import org.jspecify.annotations.NonNull;

public interface AccountNftListQueryWrapper extends QueryWrapper<AccountNftListQuery, Page<Nft>> {
  @NonNull AccountNftListQueryWrapper limit(int limit);

  @NonNull AccountNftListQueryWrapper order(Order order);

  @NonNull AccountNftListQueryWrapper senderId(CriteriaParam<AccountId> spenderId);

  @NonNull AccountNftListQueryWrapper tokenId(CriteriaParam<TokenId> tokenId);

  @NonNull AccountNftListQueryWrapper serialNumber(CriteriaParam<Long> tokenId);
}
