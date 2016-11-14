package com.app.camel.dto;

import lombok.*;

@Getter
@ToString
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private int position;
}
