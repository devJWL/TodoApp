package com.junwoo.todoapp.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
  @NotBlank(message = "코멘트 내용은 필수 입력")
  @Schema(description = "댓글 내용", example = "할일을 꼭 하자")
  private String commentContents;
}
