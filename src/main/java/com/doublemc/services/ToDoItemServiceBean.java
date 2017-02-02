package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 * Created by michal on 29.01.17.
 */
@Service
@Transactional
public class ToDoItemServiceBean {
    private final ToDoItemRepository toDoItemRepository;

    @Autowired
    public ToDoItemServiceBean(ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    public ToDoItem addToDo(ToDoItem toDoItem, User user) {
        String toDoTitle = toDoItem.getTitle();
        LocalDate toDoDueDate = toDoItem.getDueDate();
        ToDoItem newToDo = new ToDoItem(user, toDoTitle, toDoDueDate);
        return toDoItemRepository.save(newToDo);
    }

    public ToDoItem editToDo(ToDoItem newToDoItem, ToDoItem oldToDoItem) {
        String newTitle = newToDoItem.getTitle();
        LocalDate newDueDate = newToDoItem.getDueDate();
        oldToDoItem.setTitle(newTitle);
        oldToDoItem.setDueDate(newDueDate);
        return oldToDoItem;
    }

    public void deleteToDo(Long id) {
        toDoItemRepository.delete(id);
    }

    public void completeToDo(ToDoItem toDoItem) {
        toDoItem.setCompleted(true);
    }

    public boolean toDoExists(Long id) {
        if (toDoItemRepository.findOne(id) != null) {
            return true;
        }
        return false;
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
