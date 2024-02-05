package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.dto.SignupRequestDto;
import com.junwoo.todoapp.dto.SignupResponseDto;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.UserRepository;
import com.junwoo.todoapp.security.UserDetailsImpl;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public ResponseEntity<ResponseDto<SignupResponseDto>> signup(SignupRequestDto signupRequestDto) {
    Optional<User> user = userRepository.findByUsername(signupRequestDto.getUsername());
    String username = signupRequestDto.getUsername();
    String password = passwordEncoder.encode(signupRequestDto.getPassword());

    if (user.isPresent()) {
      throw new IllegalArgumentException("username이 중복되어 가입이 거부");
    }
    SignupResponseDto data = new SignupResponseDto(
        userRepository.save(new User(username, password)));
    return ResponseEntity
        .ok()
        .body(ResponseDto
            .<SignupResponseDto>builder()
            .httpStatus(HttpStatus.OK)
            .message("회원가입 성공")
            .data(data)
            .build()
        );
  }

  @Transactional
  public ResponseEntity<ResponseDto<String>> delete(String password, UserDetailsImpl userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow
        (
            () -> new NullPointerException("해당 회원이 없습니다.")
    );

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 달라서 유저 삭제 불가");
    }

    userRepository.delete(user);

    String data = user.getUsername();
    return ResponseEntity
        .ok()
        .body(ResponseDto
            .<String>builder()
            .httpStatus(HttpStatus.OK)
            .message("회원 삭제 성공")
            .data(data)
            .build()
        );
  }
}
