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

import static org.assertj.core.api.Assertions.assertThat;


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
    public void shouldHaveIdAfterSavingToDb() {
        // given
        repository.save(toDoItem1);

        // when
        long idFromDb = toDoItem1.getId();

        // then
        assertThat(idFromDb).isGreaterThan(0);

    }

    @Test
    public void shouldFindItemById () {
        // given
        repository.save(toDoItem1);

        // when
        ToDoItem toDoItemFromDb = repository.findOne(toDoItem1.getId());

        // then
        assertThat(toDoItemFromDb).isNotNull();
    }

    @Test
    public void shouldHaveTheSameTitleAfterSavingToDb() {
        // when
        ToDoItem savedToDo =  repository.save(toDoItem1);

        // then
        assertThat(savedToDo.getTitle()).isEqualTo(toDoItem1.getTitle());
    }

    @Test
    public void shouldUpdateTitleCorrectly() {
        // given
        ToDoItem savedToDo = repository.save(toDoItem1);
        savedToDo.setTitle("New title");
        repository.save(savedToDo);

        // when
        ToDoItem changedToDo = repository.findOne(savedToDo.getId());

        // then
        assertThat(changedToDo.getTitle()).isEqualTo(savedToDo.getTitle());
    }

    @Test
    public void shouldVerifyNumberOfToDosInDb() {
        //given
        repository.save(Arrays.asList(toDoItem1, toDoItem2));

        // when
        long idCountInRepository = repository.count();

        // then
        assertThat(idCountInRepository).isEqualTo(2);
    }

    @Test
    public void shouldDeleteToDoFromDb() {
        // given
        repository.save(Arrays.asList(toDoItem1, toDoItem2));

        // when
        repository.delete(toDoItem1.getId());

        // then
        assertThat(repository.count()).isEqualTo(1);
    }






}