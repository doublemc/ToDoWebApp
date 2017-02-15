package com.doublemc.controllers;

import com.doublemc.domain.ToDoItem;
import com.doublemc.services.ToDoItemServiceBean;
import com.doublemc.services.UserServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by michal on 29.01.17.
 */
@RestController
public class ToDoItemController {
    private final ToDoItemServiceBean toDoItemService;
    private final UserServiceBean userService;

    @Autowired
    ToDoItemController(ToDoItemServiceBean toDoItemService, UserServiceBean userService) {
        this.toDoItemService = toDoItemService;
        this.userService = userService;
    }

    @GetMapping("/todos")
    public ResponseEntity viewToDos(Principal principal) {
        return new ResponseEntity<>(userService.getAllToDoItems(principal), HttpStatus.OK);
    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoItem newToDo(
            @RequestBody ToDoItem toDoItem,
            Principal principal
    ) {
        return toDoItemService.addToDo(toDoItem, principal);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity deleteToDo(
            @PathVariable("id") Long itemId,
            Principal principal
    ) {
        toDoItemService.deleteToDo(itemId, principal);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity editToDo(
            @PathVariable("id") Long itemId,
            @RequestBody ToDoItem newToDoItem,
            Principal principal
    ) {
        toDoItemService.editToDo(newToDoItem, itemId, principal);
        return new ResponseEntity<>(newToDoItem, HttpStatus.OK);
    }

    @PatchMapping("/todos/{id}/complete")
    public ResponseEntity editToDo(
            @PathVariable("id") Long itemId,
            Principal principal
    ) {
        toDoItemService.completeToDo(itemId, principal);
        return new ResponseEntity<>(itemId, HttpStatus.OK);
    }
}
