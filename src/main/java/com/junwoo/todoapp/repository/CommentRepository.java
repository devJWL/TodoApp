package com.junwoo.todoapp.repository;

import com.junwoo.todoapp.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


  List<Comment> findAllByTodo_TodoId(Long todoId);
}
