package com.wimdeblauwe.examples.laravelintermediatetasklist.task;

import com.wimdeblauwe.examples.laravelintermediatetasklist.user.User;
import com.wimdeblauwe.examples.laravelintermediatetasklist.user.UserNotFoundException;
import com.wimdeblauwe.examples.laravelintermediatetasklist.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Task createTask(int userId, String name) {
        User user = userService.getUser(userId)
                               .orElseThrow(() -> new UserNotFoundException("Could not find user with id " + userId));
        return repository.save(new Task(null, name, user));
    }

    @Override
    public List<Task> getTasksByUser(int userId) {
        User user = userService.getUser(userId)
                               .orElseThrow(() -> new UserNotFoundException("Could not find user with id " + userId));

        return repository.findByUser(user);
    }

    @Override
    public Optional<Task> getTask(int taskId) {
        return repository.findById(taskId);
    }

    @Override
    public void deleteTask(Integer taskId) {
        repository.deleteById(taskId);
    }
}
