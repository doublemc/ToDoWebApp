package com.doublemc.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;

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
    @Convert(converter = LocalDateAttributeConverter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dueDate;

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

    @Override
    public String toString() {
        return "ToDoItem{" +
                "title='" + title + '\'' +
                ", completed=" + completed +
                ", dueDate=" + dueDate +
                ", user=" + user +
                '}';
    }
}
