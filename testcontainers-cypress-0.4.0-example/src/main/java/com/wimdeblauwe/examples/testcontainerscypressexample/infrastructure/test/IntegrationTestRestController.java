package com.wimdeblauwe.examples.testcontainerscypressexample.infrastructure.test;

import com.wimdeblauwe.examples.testcontainerscypressexample.todo.Todo;
import com.wimdeblauwe.examples.testcontainerscypressexample.todo.TodoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/integration-test")
public class IntegrationTestRestController {

    private final TodoService service;

    public IntegrationTestRestController(TodoService service) {
        this.service = service;
    }

    @PostMapping("/clear-all-todos")
    public void clearAllTodos() {
        service.deleteAll();
    }

    @PostMapping("/prepare-todo-list-items")
    public void prepareTodoListItems() {
        service.addTodoItem(new Todo("Add Cypress tests"));
        service.addTodoItem(new Todo("Write blog post"));
    }
}
