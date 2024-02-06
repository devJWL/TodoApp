package com.junwoo.todoapp.controller;


import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.dto.TodoRequestDto;
import com.junwoo.todoapp.dto.TodoResponseDto;
import com.junwoo.todoapp.security.UserDetailsImpl;
import com.junwoo.todoapp.service.TodoService;
import com.junwoo.todoapp.service.UserService;
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
  public ResponseEntity<ResponseDto<TodoResponseDto>> createTodo(
      @Valid @RequestBody TodoRequestDto todoRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.createTodo(todoRequestDto, userDetails.getUsername());
  }
  @GetMapping("/userId/{userId}")
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodoByUserId(
      @PathVariable Long userId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.getTodoByUserId(userDetails.getUser().getUserId(), userId);
  }

  @GetMapping("/todoId/{todoId}")
  public ResponseEntity<ResponseDto<TodoResponseDto>> getTodoByTodoId(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return todoService.getTodoByTodoId(todoId, userDetails.getUsername());
  }
  @GetMapping
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllTodos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    return todoService.getAllTodoList(userDetails.getUser().getUserId());
  }

  @GetMapping("/search")
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllTodoByTitle(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestParam String q
  ) {
    return todoService.getAllTodoByTitle(userDetails.getUser().getUserId(), q);
  }

  @PutMapping("/todoId/{todoId}")
  public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
      @PathVariable Long todoId,
      @Valid @RequestBody TodoRequestDto todoRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.updateTodo(todoRequestDto, todoId, userDetails.getUsername());
  }

  @DeleteMapping("/todoId/{todoId}")
  public ResponseEntity<ResponseDto<String>> deleteTodo(
      @PathVariable Long todoId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.deleteTodo(todoId, userDetails.getUsername());
  }
}
