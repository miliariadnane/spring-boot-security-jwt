package me.nano.springsecurityjwt.requests;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserRequest {

    @NotNull(message="first name is required")
    @Size(min=3, message ="first name should have 3 characters minimum")
    private String firstName;

    @NotNull
    @Size(min=3, message ="last name should have 3 characters minimum")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message ="Please respect email format")
    private String email;

    @NotNull(message = "Phone number is required")
    @Size(min=10,max=10, message = "Phone number should have ten numbers")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number invalid")
    private String phone;

    @NotNull(message = "Password is required")
    @Size(min=8, message ="Password should have 8 characters minimum")
    @Size(max=12, message ="Password should have 12 characters maximum")
    private String password;

    private String address;

}
