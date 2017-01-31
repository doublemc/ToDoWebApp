package com.doublemc.controllers;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.UserRepository;
import com.doublemc.services.ToDoItemServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

/**
 * Created by michal on 29.01.17.
 */
@Controller
public class ToDoItemController {


    private ToDoItemServiceBean toDoItemService;
    private UserRepository userRepository;

    @Autowired
    public ToDoItemController(ToDoItemServiceBean toDoItemService, UserRepository userRepository) {
        this.toDoItemService = toDoItemService;
        this.userRepository = userRepository;
    }

    // VIEW ALL TODOS
    @GetMapping("/")
    public String viewToDos(Model model) {
        model.addAttribute("allToDoItems", toDoItemService.listAllToDoItems());
        return "mytodos";
    }

    // CREATE NEW TODOITEM FROM SENT JSON
    @PostMapping("/todos/new")
    public String newToDo(HttpRequest request) {
        String title = ""; // extract title
        Calendar dueDate = null; // extract dueDate

        // getting logged in user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDb = userRepository.findOne(currentUser.getId());


        ToDoItem newToDoItem = new ToDoItem(userFromDb, title, dueDate);
    }

    // this function captures completed form and creates new ToDoItem + adds it to DB
    @PostMapping(value = "todoitem")
    public String saveToDo(ToDoItem toDoItem) {
        toDoItemService.updateItems(toDoItem);
        return "redirect:/todos";
    }

    @DeleteMapping(value = "/todo/delete/{id}")
    public String deleteToDo(
            @PathVariable("id") Long itemId
    ) {
//        if (toDoItemService.getToDoItemById(itemId) != null) {
        toDoItemService.deleteToDo(itemId);
        return "redirect:/todos";
//        }
//        return "failed";
    }

    @RequestMapping("/todo/edit/{id}")
    public String editToDo(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute("todoitem", toDoItemService.getToDoItemById(id));
        return "todoform";
    }

}
