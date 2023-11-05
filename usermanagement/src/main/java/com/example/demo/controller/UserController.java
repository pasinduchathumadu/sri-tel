package com.example.demo.controller;

import com.example.demo.dto.requestDto.RegisterRequestDto;
import com.example.demo.dto.requestDto.UpdateRequestDto;
import com.example.demo.dto.requestDto.ViewRequestDto;
import com.example.demo.dto.responseDto.RegisterResponseDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/adduser")
    ResponseEntity<RegisterResponseDto> addUser(RegisterRequestDto dto) {
        RegisterResponseDto  res = userService.addUser(dto);
        return ResponseEntity.ok(res);
    }
    @PostMapping("/updateuser")
    ResponseEntity<RegisterResponseDto> updateUser(UpdateRequestDto dto) {
        RegisterResponseDto  res = userService.updateUser(dto);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/delete")
    ResponseEntity<String> deleteUser(ViewRequestDto dto) {
        String res = userService.deleteUser(dto);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/view")
    ResponseEntity<RegisterResponseDto> getUser(ViewRequestDto dto){
        RegisterResponseDto  res = userService.getUser(dto);
        return ResponseEntity.ok(res);
    }

}
