package com.mine.app.repository;

import com.mine.app.exception.DuplicatedEntityException;
import com.mine.app.exception.EntityNotFoundException;
import com.mine.app.model.repository.redis.TaskDb;
import com.mine.app.model.repository.redis.TaskPk;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TaskRedisRepository implements TaskRepository {

    private final RedisTemplate<TaskPk, TaskDb> redisTaskTemplate;

    public TaskRedisRepository(RedisTemplate<TaskPk, TaskDb> redisTaskTemplate) {
        this.redisTaskTemplate = redisTaskTemplate;
    }

    @Override
    public TaskDb create(TaskDb task) {
        task.setId(UUID.randomUUID());
        final var key = TaskPk.builder()
                .id(task.getId())
                .userId(task.getUserId())
                .build();
        final var success = redisTaskTemplate.opsForValue().setIfAbsent(key, task);
        if (success != null && success) {
            return task;
        }
        throw new DuplicatedEntityException(String.format("Duplicated entity with id %s.", key));
    }

    @Override
    public TaskDb findById(String userId, UUID id) {
        final var entity = redisTaskTemplate.opsForValue().get(TaskPk.builder().id(id).userId(userId).build());
        if (entity != null) {
            return entity;
        }
        throw new EntityNotFoundException(id, "Task with specified id was not found.");
    }

    @Override
    public TaskDb update(String userId, UUID id, TaskDb task) {
        final var success = redisTaskTemplate.opsForValue().setIfPresent(TaskPk.builder().userId(userId).id(id).build(), task);
        if (success != null && success) {
            return task;
        }
        throw new EntityNotFoundException(String.format("Unable to update entity with id %s, entity does not exist", id));
    }

    @Override
    public boolean delete(String userId, UUID id) {
        final var success = redisTaskTemplate.delete(TaskPk.builder().id(id).userId(userId).build());
        return success != null && success;
    }

    @Override
    public List<TaskDb> findAll(String userId) {
        final var cursor = redisTaskTemplate.scan(ScanOptions.scanOptions().build());
        final var keys = new ArrayList<TaskPk>();
        TaskPk key;
        while (cursor.hasNext()) {
            key = cursor.next();
            if (key.getUserId().equals(userId)) {
                keys.add(key);
            }
        }
        cursor.close();
        return redisTaskTemplate.opsForValue().multiGet(keys);
    }
}
