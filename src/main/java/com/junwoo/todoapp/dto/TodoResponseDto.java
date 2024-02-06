package com.junwoo.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.junwoo.todoapp.entity.Comment;
import com.junwoo.todoapp.entity.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponseDto {

  @Schema(description = "할일 제목", example = "장보기")
  private String todoTitle;
  @Schema(description = "할일 내용", example = "우유 사기")
  private String todoContents;
  @Schema(description = "숨김처리", example = "true")
  private boolean hidden;
  @Schema(description = "완료처리", example = "true")
  private boolean completed;
  @Schema(description = "댓글 목록")
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
