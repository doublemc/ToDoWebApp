package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.UserRepository;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by michal on 10.02.17.
 */
public class UserServiceBeanTest {

    private  PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);
    private UserRepository userRepositoryMock = mock(UserRepository.class);
    private Principal principalStub = mock(Principal.class);

    private final String DEFAULT_NAME = "user";
    private final String DEFAULT_PASSWORD = "password";
    private final String DEFAULT_EMAIL = "example@example.com";


    private UserServiceBean userServiceBean = new UserServiceBean(userRepositoryMock, passwordEncoderMock);

    @Test
    public void shouldReturnTrue_whenUserDeleted() {
        // given
        User userBeingDeleted = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(userRepositoryMock.findByUsername(principalStub.getName())).thenReturn(userBeingDeleted);

        // when
        boolean removed = userServiceBean.deleteCurrentlyLoggedInUser(principalStub);

        // then
        assertThat(removed).isTrue();
        verify(userRepositoryMock).delete(userBeingDeleted);    }

    @Test
    public void shouldReturnFalse_whenUserNotFound() {
        // given
        when(userServiceBean.findLoggedInUser(principalStub)).thenReturn(null);

        // when
        boolean removed = userServiceBean.deleteCurrentlyLoggedInUser(principalStub);

        // then
        assertThat(removed).isFalse();
        verify(userRepositoryMock, never()).delete(any(User.class));
    }

    @Test
    public void shouldReturnUser_whenSavingUserToDb() {
        // given
        User userToBeSaved = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(userRepositoryMock.save(any(User.class))).thenReturn(userToBeSaved);

        // when
        User savedUser = userServiceBean.saveUser(userToBeSaved);

        // then
        assertThat(savedUser).isNotNull();
    }

    @Test
    public void shouldBeEmpty_whenThereAreNoToDos() {
        // given
        User loggedInUser = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(userServiceBean.findLoggedInUser(principalStub)).thenReturn(loggedInUser);

        // when
        Iterable<ToDoItem> toDos = userServiceBean.getAllToDoItems(principalStub);

        // then
        assertThat(toDos).isEmpty();
        verify(userRepositoryMock).findByUsername(principalStub.getName());
    }

    @Test
    public void shouldNotBeEmpty_whenToDosFound() {
        // given
        Set<ToDoItem> toDoItems = new HashSet<>();
        User userStub = mock(User.class);
        toDoItems.add(new ToDoItem(userStub, "newToDo", LocalDate.of(2015, 10, 20)));
        when(userServiceBean.findLoggedInUser(principalStub)).thenReturn(userStub);
        when(userStub.getToDoItems()).thenReturn(toDoItems);

        // when
        Iterable<ToDoItem> toDos = userServiceBean.getAllToDoItems(principalStub);

        // then
        assertThat(toDos).isNotEmpty();
        verify(userRepositoryMock).findByUsername(principalStub.getName());
    }
//
    @Test
    public void shouldReturnTrue_whenUserExists() {
        // given
        User user = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(user);

        // when
        boolean exists = userServiceBean.userWithThatUsernameAlreadyExists(user);

        // then
        assertThat(exists).isTrue();
        verify(userRepositoryMock).findByUsername(DEFAULT_NAME);
    }

    @Test
    public void shouldReturnFalse_whenUserNotFoundByUsername() {
        // given
        User user = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(null);

        // when
        boolean exists = userServiceBean.userWithThatUsernameAlreadyExists(user);

        // then
        assertThat(exists).isFalse();
    }
}