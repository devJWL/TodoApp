package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.TodoRequestDto;
import com.junwoo.todoapp.dto.TodoResponseDto;
import com.junwoo.todoapp.entity.Todo;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.TodoRepository;
import com.junwoo.todoapp.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final UserRepository userRepository;
  private final TodoRepository todoRepository;

  // TODO ResponseEntity 상황별 상태코드 올바르게 나오게 수정
  @Transactional
  public ResponseEntity<TodoResponseDto> createTodo(TodoRequestDto todoRequestDto, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new NullPointerException("해당 회원이 없습니다.")
    );
    Todo todo = new Todo(todoRequestDto, user);
    return ResponseEntity.ok(
        new TodoResponseDto(todoRepository.save(todo),
            "할일 등록 성공")
    );
  }

  @Transactional
  public ResponseEntity<TodoResponseDto> updateTodo(TodoRequestDto todoRequestDto, Long todoId, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new NullPointerException("해당 회원이 없습니다.")
    );

    Todo todo = todoRepository.findById(todoId).orElseThrow(
        () -> new NullPointerException("해당 할일을 찾을 수 없습니다.")
    );

    if (!Objects.equals(user.getUserId(), todo.getUser().getUserId())) {
      throw new IllegalArgumentException("수정자와 작성자의 ID가 다릅니다.");
    }

    todo.update(todoRequestDto);

    return ResponseEntity.ok(new TodoResponseDto(todo, "할일 수정 성공"));
  }

  public ResponseEntity<List<TodoResponseDto>> getTodoByUserId(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new NullPointerException("해당 회원이 없습니다.")
    );

    List<TodoResponseDto> todoResponseDtoList = todoRepository
        .findAllByUser_UserId(user.getUserId())
        .stream()
        .map(
        todo -> new TodoResponseDto(todo, "해당 회원의 할일 조회 성공")
        ).toList();
    return ResponseEntity.ok(todoResponseDtoList);
  }

  public ResponseEntity<List<TodoResponseDto>> getAllTodoList(Long userId) {
    List<Todo> todoList = todoRepository.findAllByUser_UserIdAndHiddenIsTrue(userId);
    todoList.addAll(todoRepository.findAllByHiddenIsFalse());

    List<TodoResponseDto> todoResponseDtoList = todoList
        .stream()
        .map(
            todo -> new TodoResponseDto(todo, "모든 회원의 할일 조회 성공")
        ).toList();
    return ResponseEntity.ok(todoResponseDtoList);
  }

  public ResponseEntity<String> deleteTodo(Long todoId, String username) {
    Todo todo = todoRepository.findById(todoId).orElseThrow(
        () -> new NullPointerException("해당 할일을 찾을 수 없습니다.")
    );

    if (!todo.getUser().getUsername().equals(username)) {
      throw new IllegalArgumentException("해당 회원의 할일 목록이 아닙니다. 삭제불가");
    }
    todoRepository.deleteById(todoId);
    return ResponseEntity.ok(todoId + "번 할일 목록을 삭제했습니다.");
  }
}
