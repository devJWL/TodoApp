package com.junwoo.todoapp.controller;


import com.junwoo.todoapp.dto.TodoRequestDto;
import com.junwoo.todoapp.dto.TodoResponseDto;
import com.junwoo.todoapp.security.UserDetailsImpl;
import com.junwoo.todoapp.service.TodoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;


  @PostMapping
  public ResponseEntity<TodoResponseDto> createTodo(
      @Valid @RequestBody TodoRequestDto todoRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      for (FieldError fieldError : fieldErrors) {
        log.error(fieldError.getDefaultMessage());
      }
      return ResponseEntity.badRequest().body(new TodoResponseDto(todoRequestDto, "할일 등록이 실패했습니다."));
    }
    return todoService.createTodo(todoRequestDto, userDetails.getUsername());
  }

  @GetMapping("/userId/{userId}")
  public ResponseEntity<List<TodoResponseDto>> getTodoByUserId(@PathVariable Long userId) {
    return todoService.getTodoByUserId(userId);
  }

  @GetMapping
  public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
    return todoService.getAllTodoList();
  }


  @PutMapping("/todoId/{todoId}")
  public ResponseEntity<TodoResponseDto> updateTodo(
      @PathVariable Long todoId,
      @Valid @RequestBody TodoRequestDto todoRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      for (FieldError fieldError : fieldErrors) {
        log.error(fieldError.getDefaultMessage());
      }
      return ResponseEntity.badRequest().body(new TodoResponseDto(todoRequestDto, "할일 수정이 실패했습니다."));
    }
    return todoService.updateTodo(todoRequestDto, todoId, userDetails.getUsername());
  }

  @DeleteMapping("/todoId/{todoId}")
  public ResponseEntity<String> deleteTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return todoService.deleteTodo(todoId, userDetails.getUsername());
  }
}
