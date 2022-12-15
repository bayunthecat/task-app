package com.mine.app.controller;

import com.mine.app.model.api.TaskApi;
import com.mine.app.model.mapper.TaskMapper;
import com.mine.app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/users/{userId}/tasks")
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper mapper;

    public TaskController(TaskService taskService, TaskMapper mapper) {
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @PostMapping
    public TaskApi create(@PathVariable("userId") String userId, @RequestBody @Valid TaskApi task) {
        final var domain = mapper.toDomain(userId, task);
        return mapper.toApi(taskService.create(domain));
    }

    @GetMapping("/{id}")
    public TaskApi findById(@PathVariable("userId") String userId, @PathVariable("id") UUID id) {
        return mapper.toApi(taskService.findById(userId, id));
    }

    @PutMapping("/{id}")
    public TaskApi update(@PathVariable("userId") String userId, @PathVariable("id") UUID id, @RequestBody TaskApi task) {
        return mapper.toApi(taskService.update(userId, id, mapper.toDomain(userId, id, task)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("userId") String userId, @PathVariable("id") UUID id) {
        taskService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<TaskApi> findAll(@PathVariable("userId") String userId) {
        return taskService.findAll(userId)
                .stream()
                .map(mapper::toApi)
                .collect(Collectors.toList());
    }
}