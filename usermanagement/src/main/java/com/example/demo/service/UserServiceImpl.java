package com.example.demo.service;

import com.example.demo.dto.requestDto.RegisterRequestDto;
import com.example.demo.dto.requestDto.UpdateRequestDto;
import com.example.demo.dto.requestDto.ViewRequestDto;
import com.example.demo.dto.responseDto.RegisterResponseDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegisterResponseDto addUser(RegisterRequestDto dto) {
        User user = new User();
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());

        User saveduser = userRepository.save(user);

        RegisterResponseDto res = new RegisterResponseDto();

        res.setEmail(saveduser.getEmail());
        res.setUserid(saveduser.getId());
        res.setFirstname(saveduser.getFirstname());
        res.setLastname(saveduser.getLastname());

        return res;

    }

    @Override
    public RegisterResponseDto getUser(ViewRequestDto dto) {

        Optional<User> userfromrepository = userRepository.findById(dto.getUserid());

        User saveduser = userfromrepository.get();

        RegisterResponseDto res = new RegisterResponseDto();

        res.setEmail(saveduser.getEmail());
        res.setUserid(saveduser.getId());
        res.setFirstname(saveduser.getFirstname());
        res.setLastname(saveduser.getLastname());


        return res;

    }

    @Override
    public String deleteUser(ViewRequestDto dto) {
       userRepository.deleteById(dto.getUserid());

        return "deleted"+dto.getUserid();
    }

    @Override
    public RegisterResponseDto updateUser(UpdateRequestDto dto) {

        Optional<User> userfromrepository = userRepository.findById(dto.getUserid());

        User saveduser = userfromrepository.get();

        saveduser.setLastname(dto.getLastname());
        saveduser.setFirstname(dto.getFirstname());
        saveduser.setEmail(dto.getEmail());

        User updateduser = userRepository.save(saveduser);



        RegisterResponseDto res = new RegisterResponseDto();

        res.setEmail(updateduser.getEmail());
        res.setUserid(updateduser.getId());
        res.setFirstname(updateduser.getFirstname());
        res.setLastname(updateduser.getLastname());

        return res;
    }
}
