package com.dto;

import lombok.Data;

@Data
public class CaseDTO {
    private int id;
    private UserDTO user;
    private String value;
    private String deadLine;
}
