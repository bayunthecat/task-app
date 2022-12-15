package com.mine.app.model.mapper;

import com.mine.app.model.api.TaskApi;
import com.mine.app.model.domain.Task;
import com.mine.app.model.repository.redis.TaskDb;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskApi toApi(Task task);

    TaskDb toRepository(Task task);

    Task toDomain(TaskDb task);

    Task toDomain(TaskApi task);

    default Task toDomain(String userId, TaskApi task) {
        final var domain = toDomain(task);
        domain.setUserId(userId);
        return domain;
    }

    default Task toDomain(String userId, UUID id, TaskApi task) {
        final var domain = toDomain(task);
        domain.setUserId(userId);
        domain.setId(id);
        return domain;
    }
}