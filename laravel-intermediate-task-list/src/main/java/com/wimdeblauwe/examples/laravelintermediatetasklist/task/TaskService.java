package com.wimdeblauwe.examples.laravelintermediatetasklist.task;

import com.wimdeblauwe.examples.laravelintermediatetasklist.user.User;

import java.util.List;

public interface TaskService {
    Task createTask(int userId, String name);

    List<Task> getTasksByUser(int userId);
}
