package com.brennanstrum.todo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;

public record Todo(
  @Id
  Integer id,
  @NotBlank
  String title,
  String desc,
  Status status,
  LocalDateTime dateCreated,
  LocalDateTime dateUpdated,
  Boolean removed
) {}