package com.doublemc.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by michal on 28.01.17.
 */

@Entity
@Table (name = "TO_DO_ITEMS")
public class ToDoItem extends BaseEntity {

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "COMPLETED")
    private boolean completed;

    @Column(name = "DUE_DATE", nullable = false)
    protected LocalDate dueDate;

    // a ToDoItem is only associated with one user
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;


    // JPA demands empty constructor
    public ToDoItem() {}

    public ToDoItem(User user, String title, LocalDate dueDate) {
        this.user = user;
        this.title = title;
        this.dueDate = dueDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
