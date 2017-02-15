package com.doublemc.services;

import com.doublemc.customexceptions.ToDoItemNotFoundException;
import com.doublemc.customexceptions.UserAccessException;
import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

/**
 * Created by michal on 29.01.17.
 */
@Service
@Transactional
public class ToDoItemServiceBean {
    private final ToDoItemRepository toDoItemRepository;
    private final UserServiceBean userService;

    @Autowired
    ToDoItemServiceBean(ToDoItemRepository toDoItemRepository, UserServiceBean userService) {
        this.toDoItemRepository = toDoItemRepository;
        this.userService = userService;
    }

    public ToDoItem addToDo(ToDoItem toDoItem, Principal principal) {
        User currentUser = userService.findLoggedInUser(principal);
        ToDoItem newToDo = new ToDoItem(currentUser, toDoItem.getTitle(), toDoItem.getDueDate());
        return toDoItemRepository.save(newToDo);
    }

    public ToDoItem editToDo(ToDoItem newToDoItem, Long itemId, Principal principal) {
        if (isToDoAccessible(itemId, principal)) {
            ToDoItem toDoFromDb = findToDoItemById(itemId);
            toDoFromDb.setTitle(newToDoItem.getTitle());
            toDoFromDb.setDueDate(newToDoItem.getDueDate());
            return toDoFromDb;
        }
        return null;
    }

    public void deleteToDo(Long id, Principal principal) {
        if(isToDoAccessible(id, principal)) {
            toDoItemRepository.delete(id);
        }
    }

    public void completeToDo(Long id, Principal principal) {
        if(isToDoAccessible(id, principal)) {
            findToDoItemById(id).setCompleted(true);
        }
    }

    private boolean isToDoAccessible(Long id, Principal principal) {
        User currentUser = userService.findLoggedInUser(principal);
        if (!toDoExists(id)) {
            throw new ToDoItemNotFoundException();
        }
        ToDoItem toDoFromDb = findToDoItemById(id);
        if (!canUserAccessToDo(toDoFromDb, currentUser)) {
            throw new UserAccessException();
        }
        return true;
    }

    private boolean toDoExists(Long id) {
        return toDoItemRepository.findOne(id) != null;
    }

    private boolean canUserAccessToDo(ToDoItem toDoItem, User user) {
        return toDoItem.getUser() == user;
    }

    private ToDoItem findToDoItemById(Long id) {
        return toDoItemRepository.findOne(id);
    }


}

