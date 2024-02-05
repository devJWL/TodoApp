package com.junwoo.todoapp.service;


import com.junwoo.todoapp.dto.CommentRequestDto;
import com.junwoo.todoapp.dto.CommentResponseDto;
import com.junwoo.todoapp.dto.ResponseDto;
import com.junwoo.todoapp.entity.Comment;
import com.junwoo.todoapp.entity.Todo;
import com.junwoo.todoapp.entity.User;
import com.junwoo.todoapp.repository.CommentRepository;
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
public class CommentService {

  private final UserRepository userRepository;
  private final TodoRepository todoRepository;
  private final CommentRepository commentRepository;


  @Transactional
  public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
      Long todoId,
      CommentRequestDto commentRequestDto,
      String username
  ) {
    User user = userRepository.findByUsername(username).orElseThrow(
        ()-> new NullPointerException("해당 회원이 없습니다.")
    );

    Todo todo = todoRepository.findById(todoId).orElseThrow(
        () -> new NullPointerException("해당 할일을 찾을 수 없습니다.")
    );

    Comment comment = new Comment(commentRequestDto, user, todo);

    CommentResponseDto data = new CommentResponseDto(commentRepository.save(comment));

    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<CommentResponseDto>builder()
                .httpStatus(HttpStatus.OK)
                .message("댓글 달기 성공")
                .data(data)
                .build()
        );
  }


  public ResponseEntity<ResponseDto<List<CommentResponseDto>>> getCommentListByTodoId(Long todoId) {
    Todo todo = todoRepository.findById(todoId).orElseThrow(
        () -> new NullPointerException("해당 할일을 찾을 수 없습니다.")
    );

    List<CommentResponseDto> data = commentRepository.findAllByTodo_TodoId(todoId)
        .stream()
        .map(CommentResponseDto::new)
        .toList();
    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<List<CommentResponseDto>>builder()
                .httpStatus(HttpStatus.OK)
                .message("특정 할일의 모든 댓글 조회 성공")
                .data(data)
                .build()
        );
  }

  @Transactional
  public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
      Long commentId,
      CommentRequestDto commentRequestDto,
      String username
  ) {
    User user = userRepository.findByUsername(username).orElseThrow(
        ()-> new NullPointerException("해당 회원이 없습니다.")
    );

    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new NullPointerException("해당 댓글을 찾을 수 없습니다.")
    );

    if (user.getUserId() != comment.getUser().getUserId()) {
      throw new IllegalArgumentException("해당 댓글에 수정권한이 없습니다.");
    }
    comment.setCommentContents(commentRequestDto.getCommentContents());
    CommentResponseDto data = new CommentResponseDto(comment);
    // Service는 동작을 보여주는 클래스
    // Dto만 반환
    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<CommentResponseDto>builder()
                .httpStatus(HttpStatus.OK)
                .message("댓글 수정 성공")
                .data(data)
                .build()
        );
  }

  @Transactional
  public ResponseEntity<ResponseDto<String>> deleteComment(Long commentId, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        ()-> new NullPointerException("해당 회원이 없습니다.")
    );

    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new NullPointerException("해당 댓글을 찾을 수 없습니다.")
    );

    if (!Objects.equals(user.getUserId(), comment.getUser().getUserId())) {
      throw new IllegalArgumentException("해당 댓글에 삭제권한이 없습니다.");
    }
    commentRepository.delete(comment);
    String data = comment.getCommentContents();

    return ResponseEntity
        .ok()
        .body(
            ResponseDto
                .<String>builder()
                .httpStatus(HttpStatus.OK)
                .message(commentId + "번 댓글 삭제 성공")
                .data(data)
                .build()
        );
  }
}
