package com.dto;

import lombok.Data;

@Data
public class UserDTO {
    int id;
    String username;
    String password;
    String confirmPassword;
}
