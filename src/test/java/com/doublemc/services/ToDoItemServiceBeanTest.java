package com.doublemc.services;

import com.doublemc.customexceptions.ToDoItemNotFoundException;
import com.doublemc.customexceptions.UserAccessException;
import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.ToDoItemRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.Principal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by michal on 14.02.17.
 */
public class ToDoItemServiceBeanTest {

    private static final String DEFAULT_TITLE = "title";
    private static final LocalDate DEFAULT_DATE = LocalDate.of(2010, 10, 20);
    private final String CHANGED_TITLE = "new title";
    private final LocalDate CHANGED_DATE = LocalDate.of(1999, 10, 20);

    private ToDoItemRepository toDoItemRepositoryMock = mock(ToDoItemRepository.class);
    private UserServiceBean userServiceMock = mock(UserServiceBean.class);
    private User userStub = mock(User.class);
    private Principal principalStub = mock(Principal.class);

    private ToDoItemServiceBean sut = new ToDoItemServiceBean(toDoItemRepositoryMock, userServiceMock);

    private ToDoItem toDoItem;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        toDoItem = new ToDoItem(userStub, DEFAULT_TITLE, DEFAULT_DATE);
    }

    @Test
    public void shouldReturnNewToDo() {
        // given
        ToDoItem toDoToBeSaved = new ToDoItem(userStub, toDoItem.getTitle(), toDoItem.getDueDate());
        when(toDoItemRepositoryMock.save(any(ToDoItem.class))).thenReturn(toDoToBeSaved);

        // when
        ToDoItem savedToDo = sut.addToDo(toDoItem, principalStub);

        // then
        assertThat(savedToDo).isNotNull();
    }

    @Test
    public void shouldReturnEditedToDo() {
        //given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(userStub);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(toDoItem);
        ToDoItem changedToDo = new ToDoItem(userStub, CHANGED_TITLE, CHANGED_DATE);

        // when
        ToDoItem editedToDo = sut.editToDo(changedToDo, toDoItem.getId(), principalStub);

        // then
        assertThat(editedToDo.getTitle()).isEqualTo(CHANGED_TITLE);
        assertThat(editedToDo.getDueDate()).isEqualTo(CHANGED_DATE);
    }

    @Test
    public void shouldThrowException_whenTryingToEditNotFoundToDo() {
        //given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(userStub);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(null);
        ToDoItem changedToDo = new ToDoItem(userStub, CHANGED_TITLE, CHANGED_DATE);

        // when
        exception.expect(ToDoItemNotFoundException.class);
        sut.editToDo(changedToDo, toDoItem.getId(), principalStub);
    }

    @Test
    public void shouldThrowException_whenTryingToEditNotOwnedToDo() {
        //given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(null);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(toDoItem);
        ToDoItem changedToDo = new ToDoItem(userStub, CHANGED_TITLE, CHANGED_DATE);

        // when
        exception.expect(UserAccessException.class);
        sut.editToDo(changedToDo, toDoItem.getId(), principalStub);
    }

    @Test
    public void shouldDeleteToDo() {
        // given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(userStub);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(toDoItem);

        // when
        sut.deleteToDo(toDoItem.getId(), principalStub);

        // then
        verify(toDoItemRepositoryMock).delete(toDoItem.getId());
    }

    @Test
    public void shouldThrowException_whenTryingToDeleteNotFoundToDo() {
        // given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(userStub);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(null);

        // when
        exception.expect(ToDoItemNotFoundException.class);
        sut.deleteToDo(toDoItem.getId(), principalStub);
    }

    @Test
    public void shouldThrowException_whenTryingToDeleteNotOwnedToDo() {
        // given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(null);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(toDoItem);

        // when
        exception.expect(UserAccessException.class);
        sut.deleteToDo(toDoItem.getId(), principalStub);
    }

    @Test
    public void shouldCompleteToDo() {
        // given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(userStub);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(toDoItem);

        // when
        sut.completeToDo(toDoItem.getId(), principalStub);

        // then
        assertThat(toDoItem.isCompleted()).isTrue();
    }

    @Test
    public void shouldThrowException_whenTryingToCompleteNotFoundToDo() {
        // given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(userStub);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(null);

        // when
        exception.expect(ToDoItemNotFoundException.class);
        sut.completeToDo(toDoItem.getId(), principalStub);
    }

    @Test
    public void shouldThrowException_whenTryingToCompleteNotOwnedToDo() {
        // given
        when(userServiceMock.findLoggedInUser(principalStub)).thenReturn(null);
        when(toDoItemRepositoryMock.findOne(toDoItem.getId())).thenReturn(toDoItem);

        // when
        exception.expect(UserAccessException.class);
        sut.completeToDo(toDoItem.getId(), principalStub);
    }
}