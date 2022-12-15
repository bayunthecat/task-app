package com.mine.app.service;

import com.mine.app.model.domain.Task;
import com.mine.app.model.mapper.TaskMapper;
import com.mine.app.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper mapper;

    public TaskService(TaskRepository taskRepository, TaskMapper mapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
    }

    public Task create(Task task) {
        final var repo = mapper.toRepository(task);
        return mapper.toDomain(taskRepository.create(repo));
    }

    public Task findById(String userId, UUID id) {
        return mapper.toDomain(taskRepository.findById(userId, id));
    }

    public Task update(String userId, UUID id, Task task) {
        taskRepository.update(userId, id, mapper.toRepository(task));
        return task;
    }

    public boolean delete(String userId, UUID id) {
        return taskRepository.delete(userId, id);
    }

    public List<Task> findAll(String userId) {
        return taskRepository.findAll(userId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}