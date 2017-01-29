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
        assertNull(user1.getId());
        repository.save(user1);
        assertNotNull(user1.getId());

    }

    @Test
    public void findUserById () {
        repository.save(user1);
        assertNotNull(repository.findOne(user1.getId()));
    }

    @Test
    public void hasTheSameEmailAfterSavingToDb() {
        User savedUser =  repository.save(user1);
        assertEquals(user1.getEmail(), savedUser.getEmail());
    }

    @Test
    public void isUpdatingEmailCorrectly() {
        User savedUser = repository.save(user1);
        savedUser.setEmail("new@example.com");
        repository.save(savedUser);
        User changedUser = repository.findOne(savedUser.getId());
        assertEquals(savedUser.getEmail(), changedUser.getEmail());
    }

    @Test
    public void verifyNumberOfUsersInDb() {
        repository.save(Arrays.asList(user1, user2));
        assertEquals(2, repository.count());
    }

    @Test
    public void verifyDeletedFileFromDb() {
        repository.save(Arrays.asList(user1, user2));
        repository.delete(user1.getId());
        assertEquals(1, repository.count());
    }

}