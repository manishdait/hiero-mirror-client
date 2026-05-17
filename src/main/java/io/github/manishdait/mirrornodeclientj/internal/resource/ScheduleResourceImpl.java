package io.github.manishdait.mirrornodeclientj.internal.resource;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.mirrornodeclientj.MirrorNodeClient;
import io.github.manishdait.mirrornodeclientj.ScheduleResource;
import io.github.manishdait.mirrornodeclientj.query.ScheduleByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.ScheduleListQuery;
import org.jspecify.annotations.NonNull;

public class ScheduleResourceImpl implements ScheduleResource {
  private final MirrorNodeClient client;

  public ScheduleResourceImpl(MirrorNodeClient client) {
    this.client = client;
  }

  @Override
  public @NonNull ScheduleListQuery findAll() {
    return new ScheduleListQuery(client);
  }

  @Override
  public @NonNull ScheduleByIdQuery findById(ScheduleId scheduleId) {
    return new ScheduleByIdQuery(client, scheduleId);
  }
}
