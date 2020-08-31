package com.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
public class PlayerDTO {

    private String userName;
    private String password;
    private String email;

}
