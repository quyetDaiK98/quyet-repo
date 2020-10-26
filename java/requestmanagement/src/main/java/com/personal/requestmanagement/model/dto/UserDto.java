package com.personal.requestmanagement.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private long id;

    private String password;

    private String userName;

    private List<String> roleCodes;
}
