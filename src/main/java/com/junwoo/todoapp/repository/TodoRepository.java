package com.junwoo.todoapp.repository;

import com.junwoo.todoapp.entity.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByUser_UserId(Long userId);
  List<Todo> findAllByHiddenIsFalse();
  List<Todo> findAllByUser_UserIdAndHiddenIsTrue(Long userId);
  List<Todo> findAllByTodoTitleContainsAndHiddenIsFalse(String title);
}
