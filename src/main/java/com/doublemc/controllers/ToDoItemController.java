package com.doublemc.controllers;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.services.ToDoItemServiceBean;
import com.doublemc.services.UserServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by michal on 29.01.17.
 */
@Controller
public class ToDoItemController {


    private ToDoItemServiceBean toDoItemService;
    private UserServiceBean userServiceBean;
    @Autowired
    public ToDoItemController(ToDoItemServiceBean toDoItemService, UserServiceBean userServiceBean) {
        this.toDoItemService = toDoItemService;
        this.userServiceBean = userServiceBean;
    }

    @GetMapping("/todos")
    public ResponseEntity<String> viewToDos(Principal principal) {
        String currentUsername = principal.getName();
        User userFromDb = userServiceBean.findUserbyUsername(currentUsername);
        if (userServiceBean.getAllToDoItems(userFromDb).iterator().hasNext()) {
            return ResponseEntity.ok(userServiceBean.getAllToDoItems(userFromDb).toString());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No todos found");
        }
    }


    // CREATE NEW TODOITEM FROM SENT JSON
    @PostMapping("/todos/new")
    public ResponseEntity<String> newToDo(
            @RequestBody ToDoItem toDoItem,
            Principal principal
    ) {
        User currentUser = userServiceBean.findUserbyUsername(principal.getName());
        return ResponseEntity.ok(toDoItemService.addToDo(toDoItem, currentUser).toString());
    }


    // TODO: 02.02.17 Use: https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<String> deleteToDo(
            @PathVariable("id") Long itemId,
            Principal principal
    ) {
        User currentUser = userServiceBean.findUserbyUsername(principal.getName());
        if (toDoItemService.getToDoItemById(itemId) != null) {
            ToDoItem toDoFromDb = toDoItemService.getToDoItemById(itemId);
            if (toDoFromDb.getUser() == currentUser) {
                    toDoItemService.deleteToDo(itemId);
                return ResponseEntity.ok("ToDo deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have access to that todo.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ToDo with that id doesn't exist.");
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> editToDo(
            @PathVariable("id") Long itemId,
            @RequestBody ToDoItem newToDoItem,
            Principal principal
    ) {
        User currentUser = userServiceBean.findUserbyUsername(principal.getName());
        if (toDoItemService.toDoExists(itemId)) {
            ToDoItem toDoFromDb = toDoItemService.getToDoItemById(itemId);
            if (toDoFromDb.getUser() == currentUser) {
                toDoItemService.editToDo(newToDoItem, toDoFromDb);
                return ResponseEntity.ok("ToDo updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have access to that ToDo.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ToDo with that id doesn't exist.");
        }
    }

}
