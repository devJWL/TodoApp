package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.CommentRequestDto;
import com.junwoo.todoapp.dto.CommentResponseDto;
import com.junwoo.todoapp.entity.Comment;
import com.junwoo.todoapp.entity.Todo;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.CommentRepository;
import com.junwoo.todoapp.repository.TodoRepository;
import com.junwoo.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final UserRepository userRepository;
  private final TodoRepository todoRepository;
  private final CommentRepository commentRepository;

  public ResponseEntity<CommentResponseDto> createComment(Long todoId, CommentRequestDto commentRequestDto, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        ()-> new NullPointerException("해당 회원이 없습니다.")
    );

    Todo todo = todoRepository.findById(todoId).orElseThrow(
        () -> new NullPointerException("해당 할일을 찾을 수 없습니다.")
    );

    Comment comment = new Comment(commentRequestDto);
    user.addComment(comment);
    todo.addComment(comment);
    return ResponseEntity.ok(new CommentResponseDto(commentRepository.save(comment), "댓글 달기 성공"));
  }

  public ResponseEntity<CommentResponseDto> updateComment(Long commentId, CommentRequestDto commentRequestDto, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        ()-> new NullPointerException("해당 회원이 없습니다.")
    );

    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new NullPointerException("해당 댓글을 찾을 수 없습니다.")
    );

    if (user.getUserId() != comment.getUser().getUserId()) {
      throw new IllegalArgumentException("해등 댓글에 수정권한이 없습니다.");
    }
    comment.setCommentContents(commentRequestDto.getCommentContents());
    return ResponseEntity.ok(new CommentResponseDto(comment, "댓글 수정 성공"));
  }

  public ResponseEntity<String> deleteComment(Long commentId, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        ()-> new NullPointerException("해당 회원이 없습니다.")
    );

    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new NullPointerException("해당 댓글을 찾을 수 없습니다.")
    );

    if (user.getUserId() != comment.getUser().getUserId()) {
      throw new IllegalArgumentException("해등 댓글에 삭제권한이 없습니다.");
    }
    commentRepository.delete(comment);
    return ResponseEntity.ok(commentId + "번 댓글 삭제");
  }


}
