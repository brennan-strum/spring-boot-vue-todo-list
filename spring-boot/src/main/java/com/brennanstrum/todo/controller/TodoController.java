package com.brennanstrum.todo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.brennanstrum.todo.model.Status;
import com.brennanstrum.todo.model.Todo;
import com.brennanstrum.todo.model.TodoRequest;
import com.brennanstrum.todo.model.TodoUpdateStatus;
import com.brennanstrum.todo.repository.TodoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todo")
@CrossOrigin
public class TodoController {

  private final TodoRepository repository;

  public TodoController(TodoRepository todoRepository) {
    this.repository = todoRepository;
  }

  private Todo generateTodo() {
    return new Todo(null, null, null, null, null, null, null);
  }

  @GetMapping("")
  public List<Todo> findAllNotRemoved() {
    return repository.findByRemoved(false);
  }

  @GetMapping("/removed")
  public List<Todo> findAllRemoved() {
    return repository.findByRemoved(true);
  }

  @GetMapping("/all")
  public List<Todo> findAll() {
    return repository.findAll();
  }

  @GetMapping("/filter/title/{keyword}")
  public List<Todo> findByTitle(@PathVariable String keyword) {
    return repository.findByTitle(keyword);
  }

  @GetMapping("/filter/desc/{keyword}")
  public List<Todo> findByDesc(@PathVariable String keyword) {
    return repository.findByDesc(keyword);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void create(@Valid @RequestBody TodoRequest todo) {
    Todo newTodo = new Todo(null, todo.title(), todo.desc(), Status.NOT_STARTED, LocalDateTime.now(), null, false);
    repository.save(newTodo);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@RequestBody TodoUpdateStatus todo, @PathVariable Integer id) {

    if (!repository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!");
    }

    // updated current todo to be flagged as removed
    Optional<Todo> currentTodo = repository.findById(id);
    Todo fallback = currentTodo.orElse(generateTodo());


    Optional<Todo> updatedTodo = currentTodo.map(updated -> 
      new Todo(
        updated.id(),
        updated.title(),
        updated.desc(),
        todo.status(),
        updated.dateCreated(),
        updated.dateUpdated(),
        updated.removed()
      )
    );

    // Save updated todo to database
    Todo updated = updatedTodo.orElse(fallback);
    repository.save(updated);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/remove/{id}")
  public void remove(@PathVariable Integer id, Todo todo) {
    if (!repository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!");
    }

    // updated current todo to be flagged as removed
    Optional<Todo> currentTodo = repository.findById(id);
    Optional<Todo> updatedTodo = currentTodo.map(updated -> 
      new Todo(
        updated.id(),
        updated.title(),
        updated.desc(),
        updated.status(),
        updated.dateCreated(),
        updated.dateUpdated(),
        Boolean.valueOf(true))
    );
    
    // Save updated todo to database
    Todo removed = updatedTodo.orElse(todo);
    repository.save(removed);
  }
}
