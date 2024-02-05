package com.junwoo.todoapp.dto;

import com.junwoo.todoapp.entity.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {
  private final String username;

  public SignupResponseDto(User user) {
    this.username = user.getUsername();
  }
}
