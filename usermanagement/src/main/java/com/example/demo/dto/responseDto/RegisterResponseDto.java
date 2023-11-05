package com.example.demo.dto.responseDto;

import lombok.Data;

@Data
public class RegisterResponseDto {
    private int userid;
    private String firstname;
    private String lastname;
    private String email;
}
