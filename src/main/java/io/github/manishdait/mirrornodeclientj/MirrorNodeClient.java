package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.resource.AccountResourceImpl;
import io.github.manishdait.mirrornodeclientj.resource.AllowanceResourceImpl;
import io.github.manishdait.mirrornodeclientj.resource.TokenResourceImpl;

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

  public AccountResource account() {
    return new AccountResourceImpl(this);
  }

  public AllowanceResource allowance() {
    return new AllowanceResourceImpl(this);
  }

  public TokenResource token() {
    return new TokenResourceImpl(this);
  }
}
