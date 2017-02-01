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


    private ToDoItemRepository toDoItemRepository;

    @Autowired
    public ToDoItemServiceBean(ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    public ToDoItem getToDoItemById(Long id) {
        return toDoItemRepository.findOne(id);
    }


    public void deleteToDo(Long id) {
        toDoItemRepository.delete(id);
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
        ToDoItem modifiedToDo = toDoItemRepository.findOne(oldToDoItem.getId());
        modifiedToDo.setTitle(newTitle);
        modifiedToDo.setDueDate(newDueDate);
        return modifiedToDo;
    }

    public boolean toDoExists(Long id) {
        if (toDoItemRepository.findOne(id) != null) {
            return true;
        }
        return false;
    }
}
