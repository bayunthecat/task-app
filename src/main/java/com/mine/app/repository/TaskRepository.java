package com.mine.app.repository;

import com.mine.app.model.repository.redis.TaskDb;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {

    TaskDb create(TaskDb task);

    TaskDb findById(String userId, UUID id);

    TaskDb update(String userId, UUID id, TaskDb task);

    boolean delete(String userId, UUID id);

    List<TaskDb> findAll(String userId);
}
