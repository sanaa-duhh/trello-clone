package com.taskfellow.trello_clone.repository;

import com.taskfellow.trello_clone.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAssignedUserId(Long userId, Pageable pageable);
    // Find tasks by User AND Priority (e.g., give me all HIGH priority tasks for this user)
    Page<Task> findByAssignedUserIdAndPriority(Long userId, String priority, Pageable pageable);

}
