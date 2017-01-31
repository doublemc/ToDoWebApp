package com.doublemc.repositories;


import com.doublemc.domain.ToDoItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by michal on 28.01.17.
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ToDoItemRepositoryTest {

    @Autowired
    ToDoItemRepository repository;

    private ToDoItem toDoItem1;
    private ToDoItem toDoItem2;

    @Before
    public void setUp() throws Exception {
//        toDoItem1 = new ToDoItem("First", LocalDate.of(2015, 6, 25));
//        toDoItem2 = new ToDoItem("Two", LocalDate.of(2017, 11, 27));
    }

    @Test
    public void hasIdAfterSavingToDb() {
        assertNull(toDoItem1.getId());
        repository.save(toDoItem1);
        assertNotNull(toDoItem1.getId());

    }

    @Test
    public void findToDoItemById () {
        repository.save(toDoItem1);
        assertNotNull(repository.findOne(toDoItem1.getId()));
    }

    @Test
    public void hasTheSameTitleAfterSavingToDb() {
        ToDoItem savedToDo =  repository.save(toDoItem1);
        assertEquals(toDoItem1.getTitle(), savedToDo.getTitle());
    }

    @Test
    public void isUpdatingTitleCorrectly() {
        ToDoItem savedToDo = repository.save(toDoItem1);
        savedToDo.setTitle("New title");
        repository.save(savedToDo);
        ToDoItem changedToDo = repository.findOne(savedToDo.getId());
        assertEquals(savedToDo.getTitle(), changedToDo.getTitle());
    }

    @Test
    public void verifyNumberOfToDosInDb() {
        repository.save(Arrays.asList(toDoItem1, toDoItem2));
        assertEquals(2, repository.count());
    }

    @Test
    public void verifyDeletedFileFromDb() {
        repository.save(Arrays.asList(toDoItem1, toDoItem2));
        repository.delete(toDoItem1.getId());
        assertEquals(1, repository.count());
    }






}