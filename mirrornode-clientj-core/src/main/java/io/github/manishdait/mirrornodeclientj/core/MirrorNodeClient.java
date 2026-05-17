package io.github.manishdait.mirrornodeclientj.core;

import io.github.manishdait.mirrornodeclientj.core.data.NetworkType;
import io.github.manishdait.mirrornodeclientj.core.internal.resource.AccountResourceImpl;
import io.github.manishdait.mirrornodeclientj.core.internal.resource.BlockResourceImpl;
import io.github.manishdait.mirrornodeclientj.core.internal.resource.NetworkResourceImpl;
import io.github.manishdait.mirrornodeclientj.core.internal.resource.ScheduleResourceImpl;
import io.github.manishdait.mirrornodeclientj.core.internal.resource.TokenResourceImpl;
import io.github.manishdait.mirrornodeclientj.core.internal.resource.TopicResourceImpl;
import io.github.manishdait.mirrornodeclientj.core.internal.resource.TransactionResourceImpl;

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

  public TopicResource topics() {
    return new TopicResourceImpl(this);
  }

  public NetworkResource network() {
    return new NetworkResourceImpl(this);
  }

  public BlockResource blocks() {
    return new BlockResourceImpl(this);
  }

  public ScheduleResource schedules() {
    return new ScheduleResourceImpl(this);
  }
}
