package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.resource.AccountResourceImpl;
import io.github.manishdait.mirrornodeclientj.resource.AllowanceResourceImpl;
import io.github.manishdait.mirrornodeclientj.resource.TokenResourceImpl;
import io.github.manishdait.mirrornodeclientj.resource.TransactionResourceImpl;

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

  public AllowanceResource allowance() {
    return new AllowanceResourceImpl(this);
  }

  public TokenResource tokens() {
    return new TokenResourceImpl(this);
  }

  public TransactionResource transactions() {
    return new TransactionResourceImpl(this);
  }
}
