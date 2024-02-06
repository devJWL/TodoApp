package com.junwoo.todoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteDto {

  @Schema(description = "회원 비밀번호", example = "junoJUNO1234")
  String password;
}
