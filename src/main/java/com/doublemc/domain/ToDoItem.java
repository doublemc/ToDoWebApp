package com.doublemc.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by michal on 28.01.17.
 */

@Entity
@Table (name = "TO_DO_ITEMS")
public class ToDoItem implements Comparable<ToDoItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TO_DO_ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "COMPLETED")
    private boolean completed;

    @Column(name = "DUE_DATE", nullable = false)
    private Date dueDate;



    // JPA demands empty contructor
    public ToDoItem() {
    }

    public ToDoItem(String title, Date dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.completed = false; // always freshly created todos aren't completed
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int compareTo(ToDoItem toDoItem) {
        return this.getId().compareTo(toDoItem.getId());
    }

    @Override
    public String toString() {
        return id + ": " + title + (isCompleted() ? " is completed" : " is not completed");
    }
}
