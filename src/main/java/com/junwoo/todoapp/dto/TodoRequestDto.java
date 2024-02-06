package com.junwoo.todoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequestDto {

  @NotBlank(message = "할일 제목은 필수값 입니다.")
  @Schema(description = "입력받은 할일제목", example = "장보기")
  private String todoTitle;
  @NotBlank(message = "할일 내용은 필수값 입니다.")
  @Schema(description = "입력받은 할일내용", example = "우유 사기")
  private String todoContents;
  @Schema(description = "입력받은 숨김옵션", example = "true")
  private boolean hidden = false;
  @Schema(description = "입력받은 완료옵션", example = "true")
  private boolean completed = false;
}
