package com.junwoo.todoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SignupRequestDto {
  @NotBlank(message = "username은 필수값 입니다.")
  @Size(min = 4, max = 10, message = "username의 길이는 4이상 10이하여야 합니다.")
  @Pattern(regexp = "^[a-z|0-9]*$")
  @Schema(description = "회원이름", example = "Juno")
  private String username;

  @NotBlank(message = "password는 필수값 입니다.")
  @Size(min = 8, max = 15, message = "password의 길이는 8이상 15이하여야 합니다.")
  @Pattern(regexp = "^[\\w]*$")
  @Schema(description = "비밀번호", example = "junoJUNO1234")
  private String password;
}
