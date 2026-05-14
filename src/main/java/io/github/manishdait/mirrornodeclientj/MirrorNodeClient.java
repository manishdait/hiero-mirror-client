package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.data.NetworkType;
import io.github.manishdait.mirrornodeclientj.internal.resource.AccountResourceImpl;
import io.github.manishdait.mirrornodeclientj.internal.resource.TokenResourceImpl;
import io.github.manishdait.mirrornodeclientj.internal.resource.TopicResourceImpl;
import io.github.manishdait.mirrornodeclientj.internal.resource.TransactionResourceImpl;

public class MirrorNodeClient {
  private final String baseUrl;

  public MirrorNodeClient(NetworkType networkType) {
    this(networkType.getUrl());
  }

  public MirrorNodeClient(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getBaseUrl() {
    return this.baseUrl;
  }

  public AccountResource accounts() {
    return new AccountResourceImpl(this);
  }

  public TokenResource tokens() {
    return new TokenResourceImpl(this);
  }

  public TransactionResource transactions() {
    return new TransactionResourceImpl(this);
  }

  public TopicResource topicResource() {
    return new TopicResourceImpl(this);
  }
}
