package com.wimdeblauwe.examples.testcontainerscypressexample.todo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private List<Todo> items = new ArrayList<>();

    public void addTodoItem(Todo todo) {
        items.add(todo);
    }

    public List<Todo> findAll() {
        return items;
    }

    public void deleteAll() {
        items.clear();
    }
}
