package com.junwoo.todoapp.dto;


import com.junwoo.todoapp.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponseDto {
  private String commentContents;
  private String message;


  public CommentResponseDto(Comment comment, String message) {
    this.commentContents = comment.getCommentContents();
    this.message = message;
  }

  public CommentResponseDto(CommentRequestDto commentRequestDto, String message) {
    this.commentContents = commentRequestDto.getCommentContents();
    this.message = message;
  }
}
