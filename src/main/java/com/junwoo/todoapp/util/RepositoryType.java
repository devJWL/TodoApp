package com.junwoo.todoapp.util;

import lombok.Getter;

@Getter
public enum RepositoryType {
  USER("userRepository"),
  TODO("todoRepository"),
  COMMENT("commentRepository");
  private final String className;

  RepositoryType(String className) {
    this.className = className;
  }

}
