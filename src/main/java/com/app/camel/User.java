package com.app.camel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {

    private final long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer isActive;


}
