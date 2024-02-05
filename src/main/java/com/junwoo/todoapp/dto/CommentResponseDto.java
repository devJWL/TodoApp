package com.junwoo.todoapp.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.junwoo.todoapp.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {
  private String commentContents;
  private String message;


  public CommentResponseDto(Comment comment) {
    this.commentContents = comment.getCommentContents();
  }
}
