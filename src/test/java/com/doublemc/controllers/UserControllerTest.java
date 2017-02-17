package com.doublemc.controllers;

import com.doublemc.domain.User;
import com.doublemc.services.UserServiceBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by michal on 16.02.17.
 */

public class UserControllerTest {

    private static final String CORRECT_REGISTER_JSON = "{\"username\":\"example\"," +
            "\"password\":\"example\"," +
            "\"email\":\"newUser@example.com\"}";

    private UserServiceBean userServiceBeanMock = mock(UserServiceBean.class);
    private ObjectMapper mapper = new ObjectMapper();
    private MockMvc mockMvc;
    private UserController sut = new UserController(userServiceBeanMock, mapper);

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void shouldReturnSuccessfulStatus_whenRegisteringNewUser() throws Exception {
        this.mockMvc.perform(post("/users").content(CORRECT_REGISTER_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"status\":\"User created.\"}"));

        verify(userServiceBeanMock).saveUser(any(User.class));
    }

    @Test
    public void shouldReturnSuccessfulStatus_whenDeletingUser() throws Exception {
        this.mockMvc.perform(delete("/users"))
                .andExpect(status().is2xxSuccessful());

        verify(userServiceBeanMock).deleteCurrentlyLoggedInUser(any(Principal.class));
    }
}