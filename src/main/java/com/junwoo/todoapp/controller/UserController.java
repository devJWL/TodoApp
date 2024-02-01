package com.junwoo.todoapp.controller;

import com.junwoo.todoapp.dto.SignupRequestDto;
import com.junwoo.todoapp.dto.SignupResponseDto;
import com.junwoo.todoapp.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")

public class UserController {

  private final UserService userService;
  @PostMapping("/signup")
  public ResponseEntity<SignupResponseDto> signup(@Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {

    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    if (!fieldErrors.isEmpty()) {
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        log.error(fieldError.getDefaultMessage());
      }
      return ResponseEntity.badRequest()
          .body(new SignupResponseDto(signupRequestDto.getUsername(), "올바르지 않은 입력으로 가입 거부"));
    }
    return userService.signup(signupRequestDto);

  }
}
