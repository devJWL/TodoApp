package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.dto.SignupRequestDto;
import com.junwoo.todoapp.dto.SignupResponseDto;
import com.junwoo.todoapp.entity.Todo;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.CommentRepository;
import com.junwoo.todoapp.repository.TodoRepository;
import com.junwoo.todoapp.repository.UserRepository;
import com.junwoo.todoapp.security.UserDetailsImpl;
import java.util.List;
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
  private final TodoRepository todoRepository;
  private final CommentRepository commentRepository;

  @Transactional
  public ResponseEntity<ResponseDto<SignupResponseDto>> signup(SignupRequestDto signupRequestDto) {
    User user = userRepository.findByUsernameOrElseThrow(signupRequestDto.getUsername());
    String username = signupRequestDto.getUsername();
    String password = passwordEncoder.encode(signupRequestDto.getPassword());

    if (user != null) {
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
    User user = userRepository.findByUsernameOrElseThrow(userDetails.getUsername());

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 불일치로 회원삭제 불가");
    }

    List<Todo> todoList = todoRepository.findAllByUser_UserId(user.getUserId());

    todoList.forEach(
        todo -> commentRepository.deleteAll(
            commentRepository.findAllByTodo_TodoId(todo.getTodoId()))
    );

    todoRepository.deleteAll(todoList);

    userRepository.delete(user);

    String data = user.getUsername();
    return ResponseEntity
        .ok()
        .body(ResponseDto
            .<String>builder()
            .httpStatus(HttpStatus.OK)
            .message("회원삭제 성공")
            .data(data)
            .build()
        );
  }
}
