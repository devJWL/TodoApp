package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.SignupRequestDto;
import com.junwoo.todoapp.dto.SignupResponseDto;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public ResponseEntity<SignupResponseDto> signup(SignupRequestDto signupRequestDto) {
    Optional<User> user = userRepository.findByUsername(signupRequestDto.getUsername());
    String username = signupRequestDto.getUsername();
    String password = passwordEncoder.encode(signupRequestDto.getPassword());

    if (user.isEmpty()) {
      userRepository.save(new User(username, password));
      return ResponseEntity.ok(new SignupResponseDto(username, "회원가입 성공"));
    }
    return ResponseEntity.badRequest()
        .body(new SignupResponseDto(username, "username이 중복되어 가입이 거부"));
  }
}
