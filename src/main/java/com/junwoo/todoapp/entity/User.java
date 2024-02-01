package com.junwoo.todoapp.entity;

import com.junwoo.todoapp.dto.SignupRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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


  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
