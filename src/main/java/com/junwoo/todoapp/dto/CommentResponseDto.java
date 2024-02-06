package com.junwoo.todoapp.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.junwoo.todoapp.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {

  @Schema(description = "댓글 작성자", example = "Juno")
  private String username;
  @Schema(description = "댓글 내용", example = "할일을 꼭 하자")
  private String commentContents;


  public CommentResponseDto(Comment comment) {
    this.username = comment.getUser().getUsername();
    this.commentContents = comment.getCommentContents();
  }
}
