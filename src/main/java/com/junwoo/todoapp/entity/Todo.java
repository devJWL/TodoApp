package com.junwoo.todoapp.entity;


import com.junwoo.todoapp.dto.TodoRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
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

  @Setter
  @OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private List<Comment> commentList;

  public Todo(TodoRequestDto todoRequestDto) {
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

  public void addComment(Comment comment) {
    comment.setTodo(this);
    this.commentList.add(comment);
  }
}
