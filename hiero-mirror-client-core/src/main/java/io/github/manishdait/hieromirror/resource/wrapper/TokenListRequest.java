package io.github.manishdait.hieromirror.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenType;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.Token;
import io.github.manishdait.hieromirror.query.TokenListQuery;
import java.util.List;
import org.jspecify.annotations.NonNull;

/** Request Wrapper for TokenListQuery. */
public interface TokenListRequest extends QueryRequest<TokenListQuery, Page<Token>> {
  /**
   * Sets the maximum number of items to return.
   *
   * @param limit maximum items to return
   * @return {@code this}
   */
  @NonNull TokenListRequest limit(int limit);

  /**
   * Sets the sorting order for the query items.
   *
   * @param order the {@link Order} sequence to enforce
   * @return {@code this}
   */
  @NonNull TokenListRequest order(Order order);

  /**
   * Sets the token name.
   *
   * @param name filter for name
   * @return {@code this}
   */
  @NonNull TokenListRequest name(String name);

  /**
   * Sets the accountId criteria param.
   *
   * @param accountId the {@link CriteriaParam} for accountId
   * @return {@code this}
   */
  @NonNull TokenListRequest accountId(CriteriaParam<AccountId> accountId);

  /**
   * Sets the tokenId criteria param.
   *
   * @param tokenId the {@link CriteriaParam} for tokenId
   * @return {@code this}
   */
  @NonNull TokenListRequest tokenId(CriteriaParam<TokenId> tokenId);

  /**
   * Sets the type filter.
   *
   * @param tokenTypes list of token type
   * @return {@code this}
   */
  @NonNull TokenListRequest type(List<TokenType> tokenTypes);

  /**
   * Sets the publicKey filter.
   *
   * @param publicKey instance of {@link PublicKey}
   * @return {@code this}
   */
  @NonNull TokenListRequest publicKey(PublicKey publicKey);
}
