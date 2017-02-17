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
    private ToDoItemRepository sut;

    private ToDoItem toDoItem1;
    private ToDoItem toDoItem2;

    @Before
    public void setUp() throws Exception {
        User user = new User("user", "password", "example@example.com");
        toDoItem1 = new ToDoItem(user, "First", LocalDate.of(2015, 6, 25));
        toDoItem2 = new ToDoItem(user, "Two", LocalDate.of(2017, 11, 27));
    }

    @Test
    public void shouldHaveIdAfterSavingToDb() {
        // given
        sut.save(toDoItem1);

        // when
        long idFromDb = toDoItem1.getId();

        // then
        assertThat(idFromDb).isGreaterThan(0);
    }

    @Test
    public void shouldFindItemById () {
        // given
        sut.save(toDoItem1);

        // when
        ToDoItem toDoItemFromDb = sut.findOne(toDoItem1.getId());

        // then
        assertThat(toDoItemFromDb).isNotNull();
    }

    @Test
    public void shouldHaveTheSameTitleAfterSavingToDb() {
        // when
        ToDoItem savedToDo =  sut.save(toDoItem1);

        // then
        assertThat(savedToDo.getTitle()).isEqualTo(toDoItem1.getTitle());
    }

    @Test
    public void shouldUpdateTitleCorrectly() {
        // given
        ToDoItem savedToDo = sut.save(toDoItem1);
        savedToDo.setTitle("New title");
        sut.save(savedToDo);

        // when
        ToDoItem changedToDo = sut.findOne(savedToDo.getId());

        // then
        assertThat(changedToDo.getTitle()).isEqualTo(savedToDo.getTitle());
    }

    @Test
    public void shouldVerifyNumberOfToDosInDb() {
        //given
        sut.save(Arrays.asList(toDoItem1, toDoItem2));

        // when
        long idCountInRepository = sut.count();

        // then
        assertThat(idCountInRepository).isEqualTo(2);
    }

    @Test
    public void shouldDeleteToDoFromDb() {
        // given
        sut.save(Arrays.asList(toDoItem1, toDoItem2));

        // when
        sut.delete(toDoItem1.getId());

        // then
        assertThat(sut.count()).isEqualTo(1);
    }






}