package com.example.demo.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.Getter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class registerDto {

    private String email;
    private String username;
    private String password;
    private String code;
}
