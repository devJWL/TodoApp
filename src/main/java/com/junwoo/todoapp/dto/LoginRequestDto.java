package com.junwoo.todoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginRequestDto {

  @Schema(description = "회원이름", example = "Juno")
  private String username;
  @Schema(description = "비밀번호", example = "abcdABCD1234")
  private String password;
}
