package com.junwoo.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.junwoo.todoapp.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponseDto {
  private String todoTitle;
  private String todoContents;
  private boolean hidden;
  private boolean completed;
  private String message;

  public TodoResponseDto(TodoRequestDto todoRequestDto, String message) {
    this.todoTitle = todoRequestDto.getTodoTitle();
    this.todoContents = todoRequestDto.getTodoContents();
    this.message = message;
  }

  public TodoResponseDto(Todo todo, String message) {
    this.todoTitle = todo.getTodoTitle();
    this.todoContents = todo.getTodoContents();
    this.hidden = todo.isHidden();
    this.completed = todo.isCompleted();
    this.message = message;
  }
}
