package com.junwoo.todoapp.util;


import com.junwoo.todoapp.entity.Item;
import com.junwoo.todoapp.repository.CommentRepository;
import com.junwoo.todoapp.repository.TodoRepository;
import com.junwoo.todoapp.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class Validator implements ValidatorFunction {

  @Autowired
  ApplicationContext ac;


  public Item findById(RepositoryType repositoryType, Long id) {
    Object repository = ac.getBean(repositoryType.getClassName());
    Optional<? extends Item> type;
    switch (repositoryType) {
      case USER -> {
        UserRepository userRepository = (UserRepository) repository;
        type = userRepository.findById(id);
      }
      case TODO -> {
        TodoRepository todoRepository = (TodoRepository) repository;
        type = todoRepository.findById(id);
      }
      case COMMENT -> {
        CommentRepository commentRepository = (CommentRepository) repository;
        type = commentRepository.findById(id);
      }
      default -> throw new IllegalArgumentException("없는 타입");
    }
    return type.orElseThrow(
        () -> new NullPointerException("없습니다.")
    );
  }

  public Item findByName(RepositoryType repositoryType, String name) {
//    Object repository = ac.getBean(repositoryType.getClassName());
//    Optional<? extends Item> type;
//    switch (repositoryType) {
//      case USER -> {
//        UserRepository userRepository = (UserRepository)repository;
//        type = userRepository.findById(name);
//      }
//      case TODO -> {
//        TodoRepository todoRepository = (TodoRepository)repository;
//        type = todoRepository.findById(name);
//      }
//      case COMMENT -> {
//        CommentRepository commentRepository = (CommentRepository)repository;
//        type = commentRepository.findById(name);
//      }
//      default -> throw new IllegalArgumentException("없는 타입");
//    }
//    return type.orElseThrow(
//        () -> new NullPointerException("없습니다.")
//    );
    return null;
  }
}

