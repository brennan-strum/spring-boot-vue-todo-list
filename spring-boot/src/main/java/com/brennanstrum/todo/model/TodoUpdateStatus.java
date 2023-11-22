package com.brennanstrum.todo.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;

public record TodoUpdateStatus(
  @Id
  Integer id,
  @NotBlank
  Status status
) {}
