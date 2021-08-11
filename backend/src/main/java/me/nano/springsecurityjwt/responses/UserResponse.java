package me.nano.springsecurityjwt.responses;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Date createdAt;
}
