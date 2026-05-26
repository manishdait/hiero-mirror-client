package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenType;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.CriteriaParam;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.model.Page;
import io.github.manishdait.hieromirror.model.Token;
import io.github.manishdait.hieromirror.query.TokenListQuery;
import io.github.manishdait.hieromirror.resource.wrapper.TokenListRequest;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class TokenListRequestImpl implements TokenListRequest {
  private final MirrorNodeClient client;

  private Integer limit;
  private Order order;
  private String name;
  private CriteriaParam<AccountId> accountId;
  private CriteriaParam<TokenId> tokenId;
  private List<TokenType> tokenTypes;
  private PublicKey publicKey;

  public TokenListRequestImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull TokenListRequest limit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public @NonNull TokenListRequest order(Order order) {
    this.order = order;
    return this;
  }

  @Override
  public @NonNull TokenListRequest name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public @NonNull TokenListRequest accountId(CriteriaParam<AccountId> accountId) {
    this.accountId = accountId;
    return this;
  }

  @Override
  public @NonNull TokenListRequest tokenId(CriteriaParam<TokenId> tokenId) {
    this.tokenId = tokenId;
    return this;
  }

  @Override
  public @NonNull TokenListRequest type(List<TokenType> tokenTypes) {
    this.tokenTypes = tokenTypes;
    return this;
  }

  @Override
  public @NonNull TokenListRequest publicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  @Override
  public @NonNull TokenListQuery buildQuery() {
    TokenListQuery query = new TokenListQuery();

    if (limit != null) {
      query.setLimit(limit);
    }

    if (order != null) {
      query.setOrder(order);
    }

    if (accountId != null) {
      query.setAccountId(accountId.getOperator(), accountId.getValue());
    }

    if (tokenTypes != null) {
      query.setTokenTypes(tokenTypes);
    }

    if (tokenId != null) {
      query.setTokenId(tokenId.getOperator(), tokenId.getValue());
    }

    if (publicKey != null) {
      query.setPublicKey(publicKey);
    }

    if (name != null) {
      query.setName(name);
    }

    return query;
  }

  @Override
  public @NonNull Page<Token> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Page<Token> call(@NonNull Duration timeout) {
    TokenListQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
