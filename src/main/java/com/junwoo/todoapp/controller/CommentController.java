package com.junwoo.todoapp.controller;


import com.junwoo.todoapp.dto.CommentRequestDto;
import com.junwoo.todoapp.dto.CommentResponseDto;
import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.security.UserDetailsImpl;
import com.junwoo.todoapp.service.CommentService;
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
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/todoId/{todoId}")
  @Operation(summary = "댓글 생성", description = "pathvariable로 할일ID, body로 댓글내용을 받아 해당 할일에 댓글 등록")
  public ResponseEntity<ResponseDto<CommentResponseDto>> createComment
      (
          @PathVariable Long todoId,
          @Valid @RequestBody CommentRequestDto commentRequestDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails
      ) {
    return commentService.createComment(todoId, commentRequestDto, userDetails.getUsername());
  }


  @GetMapping("/todoId/{todoId}")
  @Operation(summary = "특정 할일의 댓글 조회", description = "pathvariable로 할일ID을 받아 해당할일의 댓글 조회")
  public ResponseEntity<ResponseDto<List<CommentResponseDto>>> getCommentListByTodoId(@PathVariable Long todoId) {
    return commentService.getCommentListByTodoId(todoId);
  }


  @PutMapping("/commentId/{commentId}")
  @Operation(summary = "댓글 수정", description = "pathvariable로 할일ID을 받고, body로 수정할 댓글내용을 받아 댓글 수정")
  public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment
      (   @PathVariable Long commentId,
          @Valid @RequestBody CommentRequestDto commentRequestDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails
      ) {
    return commentService.updateComment(commentId, commentRequestDto, userDetails.getUsername());
  }

  @DeleteMapping("/commentId/{commentId}")
  @Operation(summary = "댓글 삭제", description = "pathvariable로 댓글ID를 받아 해당댓글 삭제")
  public ResponseEntity<ResponseDto<String>> deleteComment
      (   @PathVariable Long commentId,
          @AuthenticationPrincipal UserDetailsImpl userDetails
      ) {
    return commentService.deleteComment(commentId, userDetails.getUsername());
  }
}




