package com.houssam.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank(message = "user name is required")
    private String name;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

}
