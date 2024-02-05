package com.junwoo.todoapp.entity;


import com.junwoo.todoapp.dto.CommentRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@NoArgsConstructor
@Getter
@Setter
public class Comment extends Timestamped implements Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;
  @Column(nullable = false)
  private String commentContents;


  @ManyToOne
  @Setter
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @Setter
  @JoinColumn(name = "todo_id")
  private Todo todo;

  public Comment(CommentRequestDto commentRequestDto) {
    this.commentContents = commentRequestDto.getCommentContents();
  }
}
