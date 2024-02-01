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


}
