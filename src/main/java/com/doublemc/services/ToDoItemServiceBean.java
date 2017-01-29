package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.repositories.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by michal on 29.01.17.
 */
@Service
public class ToDoItemServiceBean implements ToDoItemService {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Override
    public Iterable<ToDoItem> listAllToDoItems() {
        return toDoItemRepository.findAll();
    }

    @Override
    public ToDoItem getToDoItemById(Long id) {
        return toDoItemRepository.findOne(id);
    }

    @Override
    public ToDoItem addNewToDo(ToDoItem toDoItem) {
        return toDoItemRepository.save(toDoItem);
    }

    @Override
    public void deleteToDo(Long id) {
        toDoItemRepository.delete(id);
    }
}
