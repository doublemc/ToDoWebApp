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

    @GetMapping("/todos")
    public String viewToDos(Model model) {
        model.addAttribute("allToDoItems", toDoItemService.listAllToDoItems());
        return "viewtodos";
    }

    // exposes new ToDoItem to Thymeleaf template (new item form)
    @GetMapping("/todo/new")
    public String newToDo(Model model) {
        model.addAttribute("todoitem", new ToDoItem());
        return "todoform";
    }

    // this function captures completed form and creates new ToDoItem + adds it to DB
    @PostMapping(value = "todoitem")
    public String saveToDo(ToDoItem toDoItem) {
        toDoItemService.addNewToDo(toDoItem);
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
