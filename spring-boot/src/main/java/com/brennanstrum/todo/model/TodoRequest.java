package com.brennanstrum.todo.model;

import jakarta.validation.constraints.NotBlank;

public record TodoRequest(
  @NotBlank
  String title,
  @NotBlank
  String desc
) {}
