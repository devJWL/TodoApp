package com.junwoo.todoapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequestDto {
  @NotBlank(message = "할일 제목은 필수값 입니다.")
  private String todoTitle;
  @NotBlank(message = "할일 내용은 필수값 입니다.")
  private String todoContents;
  private boolean isHidden = false;
  private boolean isCompleted = false;
}
