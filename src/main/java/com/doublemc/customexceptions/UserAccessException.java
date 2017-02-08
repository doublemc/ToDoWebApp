package com.doublemc.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by michal on 08.02.17.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You can only edit your ToDos")
public class UserAccessException extends RuntimeException {}
