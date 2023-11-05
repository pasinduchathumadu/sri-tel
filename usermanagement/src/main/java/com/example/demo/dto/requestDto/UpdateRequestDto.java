package com.example.demo.dto.requestDto;

import lombok.Data;

@Data
public class UpdateRequestDto {
    private int userid;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
