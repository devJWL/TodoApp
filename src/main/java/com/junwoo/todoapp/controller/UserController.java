package com.junwoo.todoapp.controller;

import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.dto.SignupRequestDto;
import com.junwoo.todoapp.dto.SignupResponseDto;
import com.junwoo.todoapp.security.UserDetailsImpl;
import com.junwoo.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")

public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입", description = "body로 이름, 비밀번호를 받아 회원가입 하기")
  @PostMapping("/signup")
  public ResponseEntity<ResponseDto<SignupResponseDto>> signup(
      @Valid @RequestBody SignupRequestDto signupRequestDto,
      HttpServletRequest httpServletRequest
  ) {
    System.out.println(httpServletRequest.getRequestURI());
    return userService.signup(signupRequestDto);
  }

  @PostMapping("/login")
  @Operation(summary = "로그인", description = "로그인 하기")
  public void login() {
  }

  @DeleteMapping
  @Operation(summary = "회원탈퇴", description = "body로 비밀번호를 받아 회원탈퇴 하기")
  public ResponseEntity<ResponseDto<String>> delete(
      @RequestBody String password,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return userService.delete(password, userDetails);
  }
}
