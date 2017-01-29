package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import org.springframework.stereotype.Service;

/**
 * Created by michal on 29.01.17.
 */

// facade pattern
@Service
public interface ToDoItemService {
    Iterable<ToDoItem> listAllToDoItems();

    ToDoItem getToDoItemById(Long id);

    ToDoItem addNewToDo(ToDoItem toDoItem);

    void deleteToDo(Long id);

    void updateItems(ToDoItem toDoItem);
}
