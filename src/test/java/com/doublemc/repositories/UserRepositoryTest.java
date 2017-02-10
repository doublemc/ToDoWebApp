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

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by michal on 29.01.17.
 */

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private User user1;
    private User user2;

    @Before
    public void setUp() throws Exception {
        user1 = new User("one", "weakpw", "one@example.com");
        user2 = new User("two", "strongpw", "two@example.com");
    }

    @Test
    public void shouldHaveIdAfterSavingToDb() {
        // given
        repository.save(user1);

        // when
        long idFromDb = user1.getId();
        // then
        assertThat(idFromDb).isGreaterThan(0);
    }

    @Test
    public void shouldFindUserById () {
        // given
        repository.save(user1);

        // when
        User userFromDb = repository.findOne(user1.getId());

        // then
        assertThat(userFromDb).isNotNull();
    }

    @Test
    public void shouldHaveSameEmailAfterSavingToDo() {
        // given
        User savedUser =  repository.save(user1);

        // when
        String emailFromDb = savedUser.getEmail();

        // then
        assertThat(emailFromDb).isEqualTo(user1.getEmail());
    }

    @Test
    public void shouldUpadteEmailCorrectly() {
        // given
        User savedUser = repository.save(user1);
        savedUser.setEmail("new@example.com");
        repository.save(savedUser);

        // when
        User changedUser = repository.findOne(savedUser.getId());

        // then
        assertThat(changedUser.getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    public void shouldVerifyNumberOfUsersInDb() {
        // given
        repository.save(Arrays.asList(user1, user2));

        // when
        long idCountInRepository = repository.count();

        // then
        assertThat(idCountInRepository).isEqualTo(2);
    }

    @Test
    public void shouldDeleteUserFromDb() {
        // given
        repository.save(Arrays.asList(user1, user2));

        // when
        repository.delete(user1.getId());

        // then
        assertThat(repository.findOne(user1.getId())).isNull();
    }
}