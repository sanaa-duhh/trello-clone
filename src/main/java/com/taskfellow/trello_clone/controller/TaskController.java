package com.taskfellow.trello_clone.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.taskfellow.trello_clone.dto.TaskRequest;
import com.taskfellow.trello_clone.entity.Task;
import com.taskfellow.trello_clone.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // 1. Create Task
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(request));
    }

    // 2. Get All My Tasks
    @GetMapping
    public ResponseEntity<Page<Task>> getMyTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String priority
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return ResponseEntity.ok(taskService.getMyTasks(priority, pageable));
    }

    // 3. Update Task (THIS IS WHAT YOU WERE MISSING)
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    // 4. Delete Task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @PostMapping(value = "/{taskId}/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Task> uploadFile(@PathVariable Long taskId, @RequestParam("file") MultipartFile file) {
        try {
            Task updatedTask = taskService.uploadAttachment(taskId, file);
            return ResponseEntity.ok(updatedTask);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // Return 500 if save fails
        }
    }
}