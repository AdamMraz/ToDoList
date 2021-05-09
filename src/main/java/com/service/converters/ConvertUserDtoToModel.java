package com.service.converters;

import com.dto.UserDTO;
import com.model.User;

public class ConvertUserDtoToModel {
    public static User ConverterDtoToModel(UserDTO userDTO) {
        try {
            return User.builder()
                    .id(userDTO.getId())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();
        }
        catch (Exception e) {
            return User.builder()
                    .id(0)
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();
        }
    }
}
