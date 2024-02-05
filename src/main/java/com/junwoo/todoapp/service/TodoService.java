package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.dto.TodoRequestDto;
import com.junwoo.todoapp.dto.TodoResponseDto;
import com.junwoo.todoapp.entity.Todo;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.TodoRepository;
import com.junwoo.todoapp.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<ResponseDto<TodoResponseDto>> createTodo(
      TodoRequestDto todoRequestDto,
      String username
  ) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new NullPointerException("해당 회원이 없습니다.")
    );

    Todo todo = new Todo(todoRequestDto);
    user.addTodo(todo);
    TodoResponseDto data = new TodoResponseDto(todoRepository.save(todo));

    return ResponseEntity
        .ok()
        .body(ResponseDto
            .<TodoResponseDto>builder()
            .httpStatus(HttpStatus.OK)
            .message("할일 등록 성공")
            .data(data)
            .build()
        );
  }

  // 애가 좀 이상하다.  // 다른사람의 Hidden을 가져온다.
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodoByUserId(
      Long myId,
      Long userId
  ) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new NullPointerException("해당 회원이 없습니다.")
    );

    List<TodoResponseDto> data = null;
    if (myId == userId) {
      data = todoRepository.findAllByUser_UserId(myId)
          .stream()
          .map(TodoResponseDto::new)
          .toList();
    } else {
      data = todoRepository
          .findAllByUser_UserIdAndHiddenIsFalse(userId)
          .stream()
          .map(TodoResponseDto::new)
          .toList();
    }
    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<List<TodoResponseDto>>builder()
                .httpStatus(HttpStatus.OK)
                .message("특정 ID회원 할일 목록 조회 성공")
                .data(data)
                .build()
        );
  }

  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllTodoList(Long userId) {
    List<TodoResponseDto> data = todoRepository
        .findAllByUser_UserIdOrHiddenIsFalse(userId)
        .stream()
        .map(TodoResponseDto::new)
        .toList();
    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<List<TodoResponseDto>>builder()
                .httpStatus(HttpStatus.OK)
                .message("모든 회원 할일 목록 조회 성공")
                .data(data)
                .build()
        );
  }

  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllTodoByTitle(Long myId, String q) {
    List<TodoResponseDto> data = todoRepository
        .getAllTodoByTitle(myId, q)
        .stream()
        .map(TodoResponseDto::new)
        .toList();
    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<List<TodoResponseDto>>builder()
                .httpStatus(HttpStatus.OK)
                .message("제목으로 할일 검색 성공")
                .data(data)
                .build()
        );
  }

  @Transactional
  public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
      TodoRequestDto todoRequestDto,
      Long todoId,
      String username
  ) {
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
    TodoResponseDto data = new TodoResponseDto(todo);

    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<TodoResponseDto>builder()
                .httpStatus(HttpStatus.OK)
                .message("할일 수정 성공")
                .data(data)
                .build()
        );
  }


  @Transactional
  public ResponseEntity<ResponseDto<String>> deleteTodo(Long todoId, String username) {
    Todo todo = todoRepository.findById(todoId).orElseThrow(
        () -> new NullPointerException("해당 할일을 찾을 수 없습니다.")
    );

    if (!todo.getUser().getUsername().equals(username)) {
      throw new IllegalArgumentException("해당 회원의 할일 목록이 아닙니다. 삭제불가");
    }
    todoRepository.deleteById(todoId);
    String data = todo.getTodoTitle();

    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<String>builder()
                .httpStatus(HttpStatus.OK)
                .message(todoId + "번 할일 삭제 성공")
                .data(data)
                .build()
        );
  }
}
