package com.wimdeblauwe.examples.laravelintermediatetasklist.task;

public interface TaskService {
    Task createTask(int userId, String name);
}
