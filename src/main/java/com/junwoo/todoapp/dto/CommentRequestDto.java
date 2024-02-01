package com.junwoo.todoapp.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
  @NotBlank(message = "코멘트 내용은 필수 입력")
  private String commentContents;
}
