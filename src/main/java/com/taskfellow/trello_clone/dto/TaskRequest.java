package com.taskfellow.trello_clone.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {
    @NotBlank(message= "Title is required")
    private String title;

    private String description;

    //high, medium, low
    private String priority;

    @Future(message = "Deadline must be in the future")
    private LocalDateTime deadline;
}
