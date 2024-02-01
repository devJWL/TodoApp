package com.junwoo.todoapp.dto;

import com.junwoo.todoapp.entity.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {
  private final String username;
  private final String errorMessage;
  public SignupResponseDto(String username, String errorMessage) {
      this.username = username;
      this.errorMessage = errorMessage;
  }
}