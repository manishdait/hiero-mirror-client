package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.ScheduleInfo;
import io.github.manishdait.hieromirror.query.ScheduleQuery;
import java.util.Optional;

/** Request Wrapper for ScheduleQuery. */
public interface ScheduleRequest extends QueryRequest<ScheduleQuery, Optional<ScheduleInfo>> {}
