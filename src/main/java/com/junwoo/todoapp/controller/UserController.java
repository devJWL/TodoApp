package com.junwoo.todoapp.controller;

import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.dto.SignupRequestDto;
import com.junwoo.todoapp.dto.SignupResponseDto;
import com.junwoo.todoapp.security.UserDetailsImpl;
import com.junwoo.todoapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")

public class UserController {

  private final UserService userService;
  @PostMapping("/signup")
  public ResponseEntity<ResponseDto<SignupResponseDto>> signup(
      @Valid SignupRequestDto signupRequestDto,
      HttpServletRequest httpServletRequest
  ) {
    System.out.println(httpServletRequest.getRequestURI());
    return userService.signup(signupRequestDto);
  }

  @DeleteMapping
  public ResponseEntity<ResponseDto<String>> delete(
      @RequestParam String password,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return userService.delete(password, userDetails);
  }
}
