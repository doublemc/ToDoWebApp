package com.doublemc.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by michal on 28.01.17.
 */

@Entity
@Table (name = "USERS")
public class User extends BaseEntity {

    @Column(name = "USERNAME")
    private String username;

    // TODO: 28.01.17 Find a way to store hashed and salted pws in DB
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    // user can have many ToDoItems
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<ToDoItem> toDoItems;

    // JPA demands empty constructor
    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Set<ToDoItem> getToDoItems() {
        return toDoItems;
    }

    public void setToDoItems(Set<ToDoItem> toDoItems) {
        this.toDoItems = toDoItems;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getId() + ": " + username;
    }
}
