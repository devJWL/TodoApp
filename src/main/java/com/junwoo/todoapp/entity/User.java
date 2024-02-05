package com.junwoo.todoapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@jakarta.persistence.Entity
@NoArgsConstructor
@Getter
@Table(name = "user")
public class User extends Timestamped implements Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @Column(unique = true, nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;


  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "user")
  private final List<Todo> todoList = new ArrayList<>();
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "user")
  private final List<Comment> commentList = new ArrayList<>();

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void addTodo(Todo todo) {
    todoList.add(todo);
    todo.setUser(this);
  }

  public void addComment(Comment comment) {
    commentList.add(comment);
    comment.setUser(this);
  }
}
