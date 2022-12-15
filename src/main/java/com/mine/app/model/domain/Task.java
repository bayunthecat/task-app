package com.mine.app.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private UUID id;

    private String userId;

    private String description;

    private LocalDate dueDate;

    private TaskState state;
}