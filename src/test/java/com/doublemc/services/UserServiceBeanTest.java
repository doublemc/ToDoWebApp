package com.doublemc.services;

import com.doublemc.customexceptions.UserAlreadyDeletedException;
import com.doublemc.customexceptions.UsernameAlreadyExistsException;
import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
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

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private UserServiceBean sut = new UserServiceBean(userRepositoryMock, passwordEncoderMock);

    @Test
    public void shouldCallDeleteOnce() {
        // given
        User userBeingDeleted = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(userRepositoryMock.findByUsername(principalStub.getName())).thenReturn(userBeingDeleted);

        // when
        sut.deleteCurrentlyLoggedInUser(principalStub);

        // then
        verify(userRepositoryMock).delete(userBeingDeleted);    }

    @Test
    public void shouldThrowException_whenUserAlreadyDeleted() {
        // given
        when(sut.findLoggedInUser(principalStub)).thenReturn(null);

        // when
        exception.expect(UserAlreadyDeletedException.class);
        sut.deleteCurrentlyLoggedInUser(principalStub);
    }

    @Test
    public void shouldReturnUser_whenSavingUserToDb() {
        // given
        User userToBeSaved = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(userRepositoryMock.save(any(User.class))).thenReturn(userToBeSaved);

        // when
        User savedUser = sut.saveUser(userToBeSaved);

        // then
        assertThat(savedUser).isNotNull();
    }

    @Test
    public void shouldThrowException_whenUsernameAlreadyExistsInDb() {
        // given
        User userToBeSaved = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(userRepositoryMock.findByUsername(userToBeSaved.getUsername())).thenReturn(userToBeSaved);

        // when
        exception.expect(UsernameAlreadyExistsException.class);
        sut.saveUser(userToBeSaved);
    }

    @Test
    public void shouldBeEmpty_whenThereAreNoToDos() {
        // given
        User loggedInUser = new User(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        when(sut.findLoggedInUser(principalStub)).thenReturn(loggedInUser);

        // when
        Iterable<ToDoItem> toDos = sut.getAllToDoItems(principalStub);

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
        when(sut.findLoggedInUser(principalStub)).thenReturn(userStub);
        when(userStub.getToDoItems()).thenReturn(toDoItems);

        // when
        Iterable<ToDoItem> toDos = sut.getAllToDoItems(principalStub);

        // then
        assertThat(toDos).isNotEmpty();
        verify(userRepositoryMock).findByUsername(principalStub.getName());
    }
}