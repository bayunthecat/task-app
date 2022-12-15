package com.mine.app.model.api;

import com.mine.app.model.domain.TaskState;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TaskApi {

    private UUID id;

    private String userId;

    @NotBlank
    private String description;

    @FutureOrPresent
    private LocalDate dueDate;

    @NotNull
    private TaskState state;
}