package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by michal on 29.01.17.
 */
@Service
@Transactional
public class ToDoItemServiceBean {
    private final ToDoItemRepository toDoItemRepository;

    @Autowired
    ToDoItemServiceBean(ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    public ToDoItem addToDo(ToDoItem toDoItem, User user) {
        ToDoItem newToDo = new ToDoItem(user, toDoItem.getTitle(), toDoItem.getDueDate());
        return toDoItemRepository.save(newToDo);
    }

    public ToDoItem editToDo(ToDoItem newToDoItem, ToDoItem oldToDoItem) {
        oldToDoItem.setTitle(newToDoItem.getTitle());
        oldToDoItem.setDueDate(newToDoItem.getDueDate());
        return oldToDoItem;
    }

    public void deleteToDo(Long id) {
        toDoItemRepository.delete(id);
    }

    public void completeToDo(ToDoItem toDoItem) {
        toDoItem.setCompleted(true);
    }

    public boolean toDoExists(Long id) {
        return toDoItemRepository.findOne(id) != null;
    }

    public boolean canUserAccessToDo(ToDoItem toDoItem, User user) {
        if (toDoItem.getUser() == user) {
            return true;
        }
        return false;
    }

    public ToDoItem findToDoItemById(Long id) {
        return toDoItemRepository.findOne(id);
    }
}
