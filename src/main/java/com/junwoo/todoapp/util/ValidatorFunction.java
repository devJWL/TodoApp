package com.junwoo.todoapp.util;

import com.junwoo.todoapp.entity.Item;

public interface ValidatorFunction {

  Item findById(RepositoryType type, Long id);

  Item findByName(RepositoryType repositoryType, String name);
}
