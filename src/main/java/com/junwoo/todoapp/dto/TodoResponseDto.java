package com.junwoo.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.junwoo.todoapp.entity.Comment;
import com.junwoo.todoapp.entity.Todo;
import java.util.List;
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
  private List<CommentResponseDto> commentResponseDtoList;

  public TodoResponseDto(Todo todo) {
    this.todoTitle = todo.getTodoTitle();
    this.todoContents = todo.getTodoContents();
    this.hidden = todo.isHidden();
    this.completed = todo.isCompleted();
  }

  public TodoResponseDto(Todo todo, List<Comment> commentList) {
    this.todoTitle = todo.getTodoTitle();
    this.todoContents = todo.getTodoContents();
    this.hidden = todo.isHidden();
    this.completed = todo.isCompleted();
    commentResponseDtoList = commentList.stream().map(CommentResponseDto::new).toList();
  }
}
