package com.wimdeblauwe.examples.testcontainerscypressexample.todo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private List<Todo> items = new ArrayList<>();

    public TodoService() {
        items.add(new Todo("Add Cypress tests"));
        items.add(new Todo("Write blog post"));
    }

    public void addTodoItem(Todo todo) {
        items.add(todo);
    }

    public List<Todo> findAll() {
        return items;
    }
}
