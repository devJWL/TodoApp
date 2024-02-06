package com.junwoo.todoapp.repository;

import com.junwoo.todoapp.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  default User findByUsernameOrElseThrow(String username) {
    return findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("해당 회원이 없습니다.")
    );
  }
}
