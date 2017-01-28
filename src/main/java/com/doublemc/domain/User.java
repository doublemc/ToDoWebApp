package com.doublemc.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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

    @OneToMany
    @JoinColumn(name = "USER_ID")
    private Set<ToDoItem> toDoItems;


    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

}
