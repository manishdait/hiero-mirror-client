package io.github.manishdait.mirrornodeclientj;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.mirrornodeclientj.query.ScheduleByIdQuery;
import io.github.manishdait.mirrornodeclientj.query.ScheduleListQuery;
import org.jspecify.annotations.NonNull;

public interface ScheduleResource {
  @NonNull ScheduleListQuery findAll();

  default @NonNull ScheduleByIdQuery findById(String scheduleId) {
    return findById(ScheduleId.fromString(scheduleId));
  }

  @NonNull ScheduleByIdQuery findById(ScheduleId scheduleId);
}
