package com.doublemc.repositories;

import com.doublemc.domain.ToDoItem;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michal on 28.01.17.
 */

public interface ToDoItemRepository extends CrudRepository<ToDoItem, Long> {

}
