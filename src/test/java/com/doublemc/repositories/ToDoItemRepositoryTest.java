package com.doublemc.repositories;


import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
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


/**
 * Created by michal on 28.01.17.
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ToDoItemRepositoryTest {

    @Autowired
    private ToDoItemRepository repository;

    private ToDoItem toDoItem1;
    private ToDoItem toDoItem2;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("user", "password", "example@example.com");
        toDoItem1 = new ToDoItem(user, "First", LocalDate.of(2015, 6, 25));
        toDoItem2 = new ToDoItem(user, "Two", LocalDate.of(2017, 11, 27));
    }

    @Test
    public void hasIdAfterSavingToDb() {
        // when
        repository.save(toDoItem1);

        // then
        assertNotNull(toDoItem1.getId());

    }

    @Test
    public void findToDoItemById () {
        // when
        repository.save(toDoItem1);

        // then
        assertNotNull(repository.findOne(toDoItem1.getId()));
    }

    @Test
    public void hasTheSameTitleAfterSavingToDb() {
        // when
        ToDoItem savedToDo =  repository.save(toDoItem1);

        // then
        assertEquals(toDoItem1.getTitle(), savedToDo.getTitle());
    }

    @Test
    public void isUpdatingTitleCorrectly() {
        // given
        ToDoItem savedToDo = repository.save(toDoItem1);
        savedToDo.setTitle("New title");

        // when
        repository.save(savedToDo);

        // then
        ToDoItem changedToDo = repository.findOne(savedToDo.getId());
        assertEquals(savedToDo.getTitle(), changedToDo.getTitle());
    }

    @Test
    public void verifyNumberOfToDosInDb() {
        // when
        repository.save(Arrays.asList(toDoItem1, toDoItem2));

        // then
        assertEquals(2, repository.count());
    }

    @Test
    public void verifyDeletedFileFromDb() {
        // given
        repository.save(Arrays.asList(toDoItem1, toDoItem2));

        // when
        repository.delete(toDoItem1.getId());

        // then
        assertEquals(1, repository.count());
    }






}