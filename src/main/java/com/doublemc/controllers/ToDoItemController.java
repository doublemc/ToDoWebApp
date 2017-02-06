package com.doublemc.controllers;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.services.ToDoItemServiceBean;
import com.doublemc.services.UserServiceBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    private final ObjectMapper mapper;

    @Autowired
    ToDoItemController(ToDoItemServiceBean toDoItemService, UserServiceBean userService, ObjectMapper mapper) {
        this.toDoItemService = toDoItemService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/todos")
    public ResponseEntity viewToDos(Principal principal) {
        ObjectNode jsonObject = mapper.createObjectNode();
        User currentUser = userService.findLoggedInUser(principal);
        if (userService.getAllToDoItems(currentUser) != null) {
            return new ResponseEntity<>(userService.getAllToDoItems(currentUser), HttpStatus.OK);
        } else {
            jsonObject.put("status", "You haven't added any ToDos yet");
            return new ResponseEntity<>(jsonObject, HttpStatus.NO_CONTENT);
        }
    }

    // CREATE NEW TODOITEM FROM SENT JSON
    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoItem newToDo(
            @RequestBody ToDoItem toDoItem,
            Principal principal
    ) {
        User currentUser = userService.findLoggedInUser(principal);
        return toDoItemService.addToDo(toDoItem, currentUser);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity deleteToDo(
            @PathVariable("id") Long itemId,
            Principal principal
    ) {
        ObjectNode jsonObject = mapper.createObjectNode();
        User currentUser = userService.findLoggedInUser(principal);
        if (toDoItemService.toDoExists(itemId)) {
            ToDoItem toDoFromDb = toDoItemService.findToDoItemById(itemId);
            if (toDoItemService.canUserAccessToDo(toDoFromDb, currentUser)) {
                toDoItemService.deleteToDo(itemId);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                jsonObject.put("status", "You can only delete your ToDos");
                return new ResponseEntity<>(jsonObject, HttpStatus.FORBIDDEN);
            }
        } else {
            jsonObject.put("status", "ToDo with that ID doesn't exist.");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity editToDo(
            @PathVariable("id") Long itemId,
            @RequestBody ToDoItem newToDoItem,
            Principal principal
    ) {
        ObjectNode jsonObject = mapper.createObjectNode();
        User currentUser = userService.findLoggedInUser(principal);
        if (toDoItemService.toDoExists(itemId)) {
            ToDoItem toDoFromDb = toDoItemService.findToDoItemById(itemId);
            if (toDoItemService.canUserAccessToDo(toDoFromDb, currentUser)) {
                toDoItemService.editToDo(newToDoItem, toDoFromDb);
                return new ResponseEntity<>(newToDoItem, HttpStatus.OK);
            } else {
                jsonObject.put("status", "You can only edit your ToDos");
                return new ResponseEntity<>(jsonObject, HttpStatus.FORBIDDEN);
            }
        } else {
            jsonObject.put("status", "ToDo with that ID doesn't exist.");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/todos/{id}/complete")
    public ResponseEntity editToDo(
            @PathVariable("id") Long itemId,
            Principal principal
    ) {
        ObjectNode jsonObject = mapper.createObjectNode();
        User currentUser = userService.findLoggedInUser(principal);
        if (toDoItemService.toDoExists(itemId)) {
            ToDoItem toDoFromDb = toDoItemService.findToDoItemById(itemId);
            if (toDoItemService.canUserAccessToDo(toDoFromDb, currentUser)) {
                toDoItemService.completeToDo(toDoFromDb);
                return new ResponseEntity<>(toDoFromDb, HttpStatus.OK);
            } else {
                jsonObject.put("status", "You can only complete your ToDos");
                return new ResponseEntity<>(jsonObject, HttpStatus.FORBIDDEN);
            }
        } else {
            jsonObject.put("status", "ToDo with that ID doesn't exist.");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
    }
}
