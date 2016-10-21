package com.app.camel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

    private final long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer isActive;

    public User(long id, String firstName, String lastName, String email, Integer isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isActive = isActive;
    }

}
