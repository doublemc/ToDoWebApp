package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.ToDoItemRepository;
import com.doublemc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by michal on 29.01.17.
 */
@Service
public class ToDoItemServiceBean {


    private ToDoItemRepository toDoItemRepository;
    private UserRepository userRepository;

    @Autowired
    public ToDoItemServiceBean(ToDoItemRepository toDoItemRepository, UserRepository userRepository) {
        this.toDoItemRepository = toDoItemRepository;
        this.userRepository = userRepository;
    }

    public ToDoItem getToDoItemById(Long id) {
        return toDoItemRepository.findOne(id);
    }


    public void deleteToDo(Long id) {
        toDoItemRepository.delete(id);
    }

    public void addToDo(ToDoItem toDoItem, User user) {
        // TODO: 31.01.17 Validation using Bean Validation API
        String toDoTitle = toDoItem.getTitle();
        LocalDate toDoDueDate = toDoItem.getDueDate();
        ToDoItem newToDo = new ToDoItem(user, toDoTitle, toDoDueDate);
        toDoItemRepository.save(newToDo);
    }
}
