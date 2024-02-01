package com.junwoo.todoapp.controller;


import com.junwoo.todoapp.dto.CommentRequestDto;
import com.junwoo.todoapp.dto.CommentResponseDto;
import com.junwoo.todoapp.security.UserDetailsImpl;
import com.junwoo.todoapp.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/todoId/{todoId}")
  public ResponseEntity<CommentResponseDto> createComment
      (
          @PathVariable Long todoId,
          @Valid @RequestBody CommentRequestDto commentRequestDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails,
          BindingResult bindingResult
      ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(new CommentResponseDto(commentRequestDto, "코멘트 등록 실패"));
    }
    return commentService.createComment(todoId, commentRequestDto, userDetails.getUsername());
  }
}


