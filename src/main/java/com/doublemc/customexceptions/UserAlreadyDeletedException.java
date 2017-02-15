package com.doublemc.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by michal on 15.02.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User already deleted")
public class UserAlreadyDeletedException extends RuntimeException {}
