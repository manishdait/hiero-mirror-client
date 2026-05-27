package io.github.manishdait.hieromirror.resource.wrapper;

import io.github.manishdait.hieromirror.model.TopicMessage;
import io.github.manishdait.hieromirror.query.TopicMessageQuery;
import java.util.Optional;

/** Request Wrapper for TopicMessageQuery. */
public interface TopicMessageByTimestampRequest
    extends QueryRequest<TopicMessageQuery, Optional<TopicMessage>> {}
