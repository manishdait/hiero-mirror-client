package io.github.manishdait.hieromirror.internal.resource;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.internal.resource.wrapper.ScheduleListRequestImpl;
import io.github.manishdait.hieromirror.internal.resource.wrapper.ScheduleRequestImpl;
import io.github.manishdait.hieromirror.resource.ScheduleResource;
import io.github.manishdait.hieromirror.resource.wrapper.ScheduleListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.ScheduleRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public class ScheduleResourceImpl implements ScheduleResource {
  private final MirrorNodeClient client;

  public ScheduleResourceImpl(final @NonNull MirrorNodeClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.client = client;
  }

  @Override
  public @NonNull ScheduleListRequest findAll() {
    return new ScheduleListRequestImpl(client);
  }

  @Override
  public @NonNull ScheduleRequest findById(@NonNull ScheduleId scheduleId) {
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");
    return new ScheduleRequestImpl(client, scheduleId);
  }
}
