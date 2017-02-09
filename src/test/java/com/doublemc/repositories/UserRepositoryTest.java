package com.doublemc.repositories;

import com.doublemc.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by michal on 29.01.17.
 */

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    private User user1;
    private User user2;

    @Before
    public void setUp() throws Exception {
        user1 = new User("one", "weakpw", "one@example.com");
        user2 = new User("two", "strongpw", "two@example.com");
    }

    @Test
    public void hasIdAfterSavingToDb() {
        // when
        repository.save(user1);

        // then
        assertNotNull(user1.getId());
    }

    @Test
    public void findUserById () {
        // when
        repository.save(user1);

        // then
        assertNotNull(repository.findOne(user1.getId()));
    }

    @Test
    public void hasTheSameEmailAfterSavingToDb() {
        // when
        User savedUser =  repository.save(user1);

        // then
        assertEquals(user1.getEmail(), savedUser.getEmail());
    }

    @Test
    public void isUpdatingEmailCorrectly() {
        // given
        User savedUser = repository.save(user1);
        savedUser.setEmail("new@example.com");

        // when
        repository.save(savedUser);

        // then
        User changedUser = repository.findOne(savedUser.getId());
        assertEquals(savedUser.getEmail(), changedUser.getEmail());
    }

    @Test
    public void verifyNumberOfUsersInDb() {
        // when
        repository.save(Arrays.asList(user1, user2));

        // then
        assertEquals(2, repository.count());
    }

    @Test
    public void verifyDeletedFileFromDb() {
        // given
        repository.save(Arrays.asList(user1, user2));

        // when
        repository.delete(user1.getId());

        // then
        assertEquals(1, repository.count());
    }

}