package com.junwoo.todoapp.repository;

import com.junwoo.todoapp.entity.Todo;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByUser_UserId(Long userId);

  default Todo findByIdOrElseThrow(Long todoId) {
    return findById(todoId).orElseThrow(
        () -> new EntityNotFoundException("해당 할일이 없습니다.")
    );
  }
  List<Todo> findAllByUser_UserIdAndHiddenIsFalse(Long userId);

  List<Todo> findAllByUser_UserIdOrHiddenIsFalseOrderByCreatedAtDesc(Long userId);


  @Query("""
      select t from Todo t
      where (t.user.userId = ?1 and t.todoTitle like concat('%', ?2, '%')) 
      or (t.hidden = false and t.todoTitle like concat('%', ?2, '%'))""")
  List<Todo> getAllTodoByTitle(
      Long user_userId, String todoTitle
  );

}
