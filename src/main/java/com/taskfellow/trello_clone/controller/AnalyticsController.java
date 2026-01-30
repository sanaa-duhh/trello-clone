package com.taskfellow.trello_clone.controller;
import com.taskfellow.trello_clone.repository.TaskRepository;
import com.taskfellow.trello_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOverview() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total_users", userRepository.count());
        stats.put("total_tasks", taskRepository.count());
        // You can add more like "tasks_completed" if you have a status field
        return ResponseEntity.ok(stats);
    }
}