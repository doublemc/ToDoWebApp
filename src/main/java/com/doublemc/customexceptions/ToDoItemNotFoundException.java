package com.doublemc.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such ToDoItem")
public class ToDoItemNotFoundException extends RuntimeException {}