package com.junwoo.todoapp.dto;

import com.junwoo.todoapp.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignupResponseDto {

  @Schema(description = "로그인 된 회원이름", example = "junoJUNO1234")
  private final String username;

  public SignupResponseDto(User user) {
    this.username = user.getUsername();
  }
}
