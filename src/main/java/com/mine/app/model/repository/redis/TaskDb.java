package com.mine.app.model.repository.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskDb implements Serializable {

    private UUID id;

    private String userId;

    private String description;

    private LocalDate dueDate;

    private String state;
}
