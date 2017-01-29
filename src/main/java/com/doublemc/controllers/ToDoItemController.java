package com.doublemc.controllers;

import com.doublemc.domain.ToDoItem;
import com.doublemc.services.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michal on 29.01.17.
 */
@RestController
public class ToDoItemController {

    @Autowired
    ToDoItemService toDoItemService;

    @GetMapping("/todos/showall")
    public String viewToDos(Model model) {
        model.addAttribute("allToDoItems", toDoItemService.listAllToDoItems());
        return "viewtodos";
    }

    // exposes new ToDoItem to Thymeleaf template (new item form)
    @GetMapping("/todos/addnew")
    public String newToDo(Model model) {
        model.addAttribute("todoitem", new ToDoItem());
        return "todoform";
    }

    // this function captures completed form and creates new ToDoItem + adds it to DB
    @PostMapping("/todos/addnew")
    public String saveToDo(@ModelAttribute ToDoItem toDoItem) {
        toDoItemService.addNewToDo(toDoItem);
        return "redirect:/todos/showall";
    }

    @DeleteMapping(value = "/todos/delete/{id}")
    public String deleteToDo(@PathVariable("id") Long itemId) {
        if (toDoItemService.getToDoItemById(itemId) != null) {
            toDoItemService.deleteToDo(itemId);
            return "success";
        }
        return "failed";
    }

}
