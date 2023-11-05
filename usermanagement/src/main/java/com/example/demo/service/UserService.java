package com.example.demo.service;

import com.example.demo.dto.requestDto.RegisterRequestDto;
import com.example.demo.dto.requestDto.UpdateRequestDto;
import com.example.demo.dto.requestDto.ViewRequestDto;
import com.example.demo.dto.responseDto.RegisterResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    RegisterResponseDto addUser(RegisterRequestDto dto);
    RegisterResponseDto getUser(ViewRequestDto dto);
    String deleteUser(ViewRequestDto dto);

    RegisterResponseDto updateUser(UpdateRequestDto dto);


}
