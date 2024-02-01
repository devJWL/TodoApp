package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.TodoRequestDto;
import com.junwoo.todoapp.dto.TodoResponseDto;
import com.junwoo.todoapp.entity.Todo;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.TodoRepository;
import com.junwoo.todoapp.repository.UserRepository;
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
    Todo todo = new Todo(todoRequestDto);
    user.addTodo(todo);
    return ResponseEntity.ok(
        new TodoResponseDto(todoRepository.save(todo),
            "할일 등록 성공"));
  }

  @Transactional
  public ResponseEntity<TodoResponseDto> updateTodo(TodoRequestDto todoRequestDto, Long todoId, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new NullPointerException("해당 회원이 없습니다.")
    );

    Todo todo = todoRepository.findById(todoId).orElseThrow(
        () -> new NullPointerException("해당 할일을 찾을 수 없습니다.")
    );

    if (user.getUserId() != todo.getUser().getUserId()) {
      throw new IllegalArgumentException("수정자와 작성자의 ID가 다릅니다.");
    }

    todo.update(todoRequestDto);

    return ResponseEntity.ok(new TodoResponseDto(todo, "할일 수정 성공"));
  }
}
