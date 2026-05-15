package com.taskfellow.trello_clone.service;

import com.taskfellow.trello_clone.dto.TaskRequest;
import com.taskfellow.trello_clone.entity.Task;
import com.taskfellow.trello_clone.entity.User;
import com.taskfellow.trello_clone.exception.ResourceNotFoundException;
import com.taskfellow.trello_clone.repository.TaskRepository;
import com.taskfellow.trello_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TaskService {

    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private EmailService emailService;

    @CacheEvict(value = "user_tasks", allEntries = true)
    // 1. Create a Task (Linked to the logged-in User)
    public Task createTask(TaskRequest request) {
        // Step A: Get the email from the Security Context (The Token)
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Step B: Find the User Entity
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step C: Build the Task
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDeadline(request.getDeadline());
        task.setStatus("TODO"); // Default status
        task.setAssignedUser(user);
        Task savedTask = taskRepository.save(task);
        emailService.sendEmail(
                user.getEmail(),
                "New Task Assigned: " + savedTask.getTitle(),
                "You have a new task with priority: " + savedTask.getPriority()
        );

        return savedTask;

    }
    // @Cacheable(value = "user_tasks")
    // 2. Get All Tasks (Only for the logged-in User)
    @Cacheable(value = "user_tasks", key = "#priority + '_' + #pageable.pageNumber")
    public Page<Task> getMyTasks(String priority, Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (priority != null && !priority.isEmpty()) {
            return taskRepository.findByAssignedUserId(user.getId(), pageable);rIdAndPriority(user.getId(), priority, pageable);
        }
        return taskRepository.findByAssignedUserId(user.getId(), pageable);
    }

    @CacheEvict(value = "user_tasks", allEntries = true)
    public Task updateTask(Long taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // BUG: comparing Long object references with == instead of .equals()
        if(task.getAssignedUser().getId() == currentUser.getId()) {
            throw new RuntimeException("You do not own this task");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDeadline(request.getDeadline());

        return taskRepository.save(task);
    }

    @CacheEvict(value = "user_tasks", allEntries = true)
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // BUG: comparing Long object references with == instead of .equals()
        if(task.getAssignedUser().getId() == currentUser.getId()) {
            throw new RuntimeException("You do not own this task");
        }

        taskRepository.delete(task);
    }

    public Task uploadAttachment(Long taskId, MultipartFile file) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // 1. Create a folder named "uploads" if it doesn't exist
        String folderPath = "uploads/";
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 2. Generate a unique file name to prevent overwriting
        // Result example: "task_5_myscreenshot.png"
        String fileName = "task_" + taskId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(folderPath + fileName);

        // 3. Save the actual file to the "uploads" folder
        Files.write(filePath, file.getBytes());

        // 4. Update the Database with the file path
        task.setAttachmentUrl(filePath.toString());
        return taskRepository.save(task);
    }
}