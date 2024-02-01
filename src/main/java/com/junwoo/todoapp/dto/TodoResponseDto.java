package com.junwoo.todoapp.dto;

import com.junwoo.todoapp.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponseDto {
  private String todoTitle;
  private String todoContents;
  private String message;

  public TodoResponseDto(TodoRequestDto todoRequestDto, String message) {
    this.todoTitle = todoRequestDto.getTodoTitle();
    this.todoContents = todoRequestDto.getTodoContents();
    this.message = message;
  }

  public TodoResponseDto(Todo todo, String message) {
    this.todoTitle = todo.getTodoTitle();
    this.todoContents = todo.getTodoContents();
    this.message = message;
  }
}
