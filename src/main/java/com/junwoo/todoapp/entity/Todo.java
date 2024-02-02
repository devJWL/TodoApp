package com.junwoo.todoapp.entity;


import com.junwoo.todoapp.dto.TodoRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Todo extends Timestamped{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long todoId;
  @Column(nullable = false)
  private String todoTitle;
  @Column(nullable = false)
  private String todoContents;
  private boolean hidden;
  private boolean completed;



  @Setter
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;


  public Todo(TodoRequestDto todoRequestDto, User user) {
    this.user = user;
    this.todoTitle = todoRequestDto.getTodoTitle();
    this.todoContents = todoRequestDto.getTodoContents();
    this.hidden = todoRequestDto.isHidden();
    this.completed = todoRequestDto.isCompleted();
  }

  public void update(TodoRequestDto todoRequestDto) {
    this.todoTitle = todoRequestDto.getTodoTitle();
    this.todoContents = todoRequestDto.getTodoContents();
    this.hidden = todoRequestDto.isHidden();
    this.completed = todoRequestDto.isCompleted();
  }
}
