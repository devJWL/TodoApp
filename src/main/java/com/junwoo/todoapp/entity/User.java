package com.junwoo.todoapp.entity;

import com.junwoo.todoapp.dto.SignupRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "user")
public class User extends Timestamped{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @Column(unique = true, nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;


  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
  @Column(name = "todo_id")
  private List<Todo> todoList = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
  @Column(name = "comment_id")
  private List<Comment> commentList = new ArrayList<>();

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void addTodo(Todo todo) {
    todo.setUser(this);
    this.todoList.add(todo);
  }

  public void addComment(Comment comment) {
    comment.setUser(this);
    this.commentList.add(comment);
  }
}
