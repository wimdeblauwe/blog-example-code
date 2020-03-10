package com.wimdeblauwe.examples.laravelintermediatetasklist.task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(int userId, String name);

    List<Task> getTasksByUser(int userId);

    Optional<Task> getTask(int taskId);

    void deleteTask(Integer taskId);
}
