package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.TodoRequestDto;
import com.junwoo.todoapp.dto.TodoResponseDto;
import com.junwoo.todoapp.entity.Todo;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.TodoRepository;
import com.junwoo.todoapp.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final UserRepository userRepository;
  private final TodoRepository todoRepository;

  @Transactional
  public ResponseEntity<TodoResponseDto> createTodo(TodoRequestDto todoRequestDto, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new NullPointerException("해당 회원이 없습니다.")
    );

    Todo todo = todoRepository.save(new Todo(todoRequestDto, user));
    return ResponseEntity.ok(new TodoResponseDto(todo, "할일 등록 성공"));
  }
}
