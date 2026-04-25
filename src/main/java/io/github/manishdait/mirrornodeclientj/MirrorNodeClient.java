package io.github.manishdait.mirrornodeclientj;

import io.github.manishdait.mirrornodeclientj.resource.AccountResourceImpl;

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
}
