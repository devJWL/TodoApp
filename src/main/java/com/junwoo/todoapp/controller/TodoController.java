package com.junwoo.todoapp.controller;


import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.dto.TodoRequestDto;
import com.junwoo.todoapp.dto.TodoResponseDto;
import com.junwoo.todoapp.security.UserDetailsImpl;
import com.junwoo.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;


  @PostMapping
  @Operation(summary = "할일 등록", description = "body로 제목, 내용을 받아 할일 등록하기")
  public ResponseEntity<ResponseDto<TodoResponseDto>> createTodo(
      @Valid @RequestBody TodoRequestDto todoRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.createTodo(todoRequestDto, userDetails.getUsername());
  }

  @GetMapping("/userId/{userId}")
  @Operation(summary = "특정 유저 할일 조회", description = "pathvariable로 회원ID를 받아 회원의 할일 조회하기")
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodoByUserId(
      @PathVariable Long userId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.getTodoByUserId(userDetails.getUser().getUserId(), userId);
  }


  @GetMapping("/todoId/{todoId}")
  @Operation(summary = "특정 할일 조회", description = "pathvariable로 할일ID를 받아 할일 조회하기")
  public ResponseEntity<ResponseDto<TodoResponseDto>> getTodoByTodoId(
      @PathVariable Long todoId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.getTodoByTodoId(todoId, userDetails.getUsername());
  }
  @GetMapping
  @Operation(summary = "모든 할일 조회", description = "모든 할일 조회하기")
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllTodos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    return todoService.getAllTodoList(userDetails.getUser().getUserId());
  }

  @GetMapping("/search")
  @Operation(summary = "할일 제목으로 검색", description = "requestparam으로 검색어를 받아 제목으로 조회")
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllTodoByTitle(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestParam String q
  ) {
    return todoService.getAllTodoByTitle(userDetails.getUser().getUserId(), q);
  }

  @PutMapping("/todoId/{todoId}")
  @Operation(summary = "할일 변경", description = "body로 입력을 받아 할일 수정")
  public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
      @PathVariable Long todoId,
      @Valid @RequestBody TodoRequestDto todoRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.updateTodo(todoRequestDto, todoId, userDetails.getUsername());
  }

  @DeleteMapping("/todoId/{todoId}")
  @Operation(summary = "할일 삭제", description = "pathvariable로 할일ID를 입력받아 해당 할일 삭제")
  public ResponseEntity<ResponseDto<String>> deleteTodo(
      @PathVariable Long todoId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.deleteTodo(todoId, userDetails.getUsername());
  }
}
