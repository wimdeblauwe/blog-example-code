package com.wimdeblauwe.examples.laravelintermediatetasklist.task;

import com.wimdeblauwe.examples.laravelintermediatetasklist.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    List<Task> findByUser(User user);
}
