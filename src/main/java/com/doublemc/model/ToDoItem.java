package com.doublemc.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by michal on 28.01.17.
 */

@Entity
@Table (name = "TO_DO_ITEM")
public class ToDoItem extends BaseEntity {


    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "COMPLETED")
    private boolean completed;

    @Column(name = "DUE_DATE", nullable = false)
    private Date dueDate;


    // JPA demands empty contructor
    public ToDoItem() {}

    public ToDoItem(String title, Date dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.completed = false; // always new todos aren't completed
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

}
