package com.app.camel.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class User {

    private final long id;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
}
