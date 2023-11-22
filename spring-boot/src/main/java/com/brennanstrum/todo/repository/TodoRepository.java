package com.brennanstrum.todo.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.brennanstrum.todo.model.Status;
import com.brennanstrum.todo.model.Todo;

import java.util.List;



public interface TodoRepository extends ListCrudRepository<Todo, Integer> {

  List<Todo> findByStatus(Status status);

  List<Todo> findByRemoved(Boolean removed);

  List<Todo> findByTitle(String title);

  List<Todo> findByDesc(String desc);
}
