package com.doublemc.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by michal on 08.02.17.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User with that username already exists")
public class UsernameAlreadyExistsException extends RuntimeException {
}
