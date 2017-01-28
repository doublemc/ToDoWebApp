package com.doublemc.repositories;


import com.doublemc.domain.ToDoItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by michal on 28.01.17.
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ToDoItemRepositoryTest {

    @Autowired
    ToDoItemRepository repository;

    ToDoItem toDoItem;

    @Before
    public void setUp() {
        toDoItem = new ToDoItem("First", new Date(20/05/1994));
    }

    @Test
    public void findSavedToDoItemById() {
        toDoItem = repository.save(toDoItem);

        assertThat(repository.findOne(toDoItem.getId()), is(toDoItem));

    }

    @Test
    public void findSavedToDoItemByDate() {
        toDoItem = repository.save(toDoItem);

        List<ToDoItem> toDoItems = repository.findByDueDate(new Date(20/05/1994));

        assertThat(toDoItems, is(notNullValue()));

        assertThat(toDoItems.contains(toDoItem), is(true));
    }

    @Test
    public void removeByTitle() {
        // create seconds toDoItem with the same name as first one
        ToDoItem toDoItem2 = new ToDoItem(toDoItem.getTitle(), new Date(20/05/2015));

        // create a third item as a control group
        ToDoItem toDoItem3 = new ToDoItem("Third", new Date(20/05/2013));

        repository.save(Arrays.asList(toDoItem, toDoItem2, toDoItem3));

        assertThat(repository.removeByTitle(toDoItem.getTitle()), is(2L));
        assertThat(repository.exists(toDoItem3.getId()), is(true));

    }


}