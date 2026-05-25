package io.github.manishdait.hieromirror.resource;

import com.hedera.hashgraph.sdk.ScheduleId;
import io.github.manishdait.hieromirror.resource.wrapper.ScheduleListRequest;
import io.github.manishdait.hieromirror.resource.wrapper.ScheduleRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

/** Access point for executing schedule related queries. */
public interface ScheduleResource {
  /** Prepares a query request to fetch list of schedule. */
  @NonNull ScheduleListRequest findAll();

  /** Prepares a query request to fetch single schedule by id. */
  default @NonNull ScheduleRequest findById(final @NonNull String scheduleId) {
    Objects.requireNonNull(scheduleId, "scheduleId must not be null");
    return findById(ScheduleId.fromString(scheduleId));
  }

  /** Prepares a query request to fetch single schedule by id. */
  @NonNull ScheduleRequest findById(final @NonNull ScheduleId scheduleId);
}
