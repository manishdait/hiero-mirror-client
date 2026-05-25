package io.github.manishdait.hieromirror.internal.resource.wrapper;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.ScheduleInfo;
import io.github.manishdait.hieromirror.query.ScheduleQuery;
import io.github.manishdait.hieromirror.resource.wrapper.ScheduleRequest;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;

public class ScheduleRequestImpl implements ScheduleRequest {
  private final MirrorNodeClient client;
  private final ScheduleId scheduleId;

  public ScheduleRequestImpl(
      final @NonNull MirrorNodeClient client, final @NonNull ScheduleId scheduleId) {
    Objects.requireNonNull(client, "client must not be null");
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");

    this.client = client;
    this.scheduleId = scheduleId;
  }

  @Override
  public @NonNull ScheduleQuery buildQuery() {
    return new ScheduleQuery().setScheduleId(scheduleId);
  }

  @Override
  public @NonNull Optional<ScheduleInfo> call() {
    return call(client.getTimeout());
  }

  @Override
  public @NonNull Optional<ScheduleInfo> call(@NonNull Duration timeout) {
    ScheduleQuery query = buildQuery();
    return query.execute(client, timeout);
  }
}
