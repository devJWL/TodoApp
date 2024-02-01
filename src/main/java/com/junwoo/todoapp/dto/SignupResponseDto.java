package com.junwoo.todoapp.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
  private final String username;
  private final String message;
  public SignupResponseDto(String username, String message) {
      this.username = username;
      this.message = message;
  }
}
