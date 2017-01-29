package com.doublemc.controllers;

import com.doublemc.model.ToDoItem;
import com.doublemc.repositories.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michal on 29.01.17.
 */
@RestController
public class ToDoItemController {

    @Autowired
    ToDoItemRepository toDoItemRepository;

    // is responsible for giving all the ToDos to Thymeleaf so it has access to them
    @GetMapping("/yourtodos")
    public String viewToDos(Model model) {
        model.addAttribute("allToDoItems", toDoItemRepository.findAll());
        return "viewtodos";
    }

    @PostMapping("/addtodo")
    public String addToDo(@ModelAttribute ToDoItem toDoItem) {
        toDoItemRepository.save(toDoItem);
        return "todoadded";
    }

    @DeleteMapping(value = "items/{id}")
    public String delete(@PathVariable("id") Long itemId, Model model) {
        ToDoItem toDoItem = toDoItemRepository.findOne(itemId);
        toDoItemRepository.delete(toDoItem.getId());

        return "itemdeleted";
    }

}
