package com.example.myorder.services;

import com.example.myorder.api.dtos.CreateUserDto;
import com.example.myorder.api.dtos.UserResponseDto;
import com.example.myorder.api.mappers.UserMapper;
import com.example.myorder.entities.User;
import com.example.myorder.exception.AlreadyExistsExption;
import com.example.myorder.exception.NotFoundExpection;
import com.example.myorder.repositories.UserRepository;
import jdk.tools.jaotc.ELFMacroAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponseDto create(CreateUserDto createUserDto){
        validateUserEmail(createUserDto.getEmail());
        userRepository.save(createUser(createUserDto));

        User user = userRepository.save(createUser(createUserDto));

        return UserMapper.toResponseDto(user);

    }

    public void validateUserEmail(String email){
        User user = userRepository.findByEmail(email);

        if(user != null){
            throw new AlreadyExistsExption("Ja existe um usuario cadastro com esse email");
        }

    }

    private User createUser(CreateUserDto createUserDto){
        return new User()
                .setName(createUserDto.getName())
                .setEmail(createUserDto.getEmail())
                .setAdress(createUserDto.getPassword())
                .setPassword(createUserDto.getPassword())
                .setPhone(createUserDto.getPhone());
    }

    public UserResponseDto findById(Integer id){
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new NotFoundExpection("Id não encontrado");
        }
        return UserMapper.toResponseDto(user.get());
    }

//    private User saveUser(User user){
//        return userRepository.save(user);
//    }



    public List<UserResponseDto> listAll(){
        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapper::toResponseDto).collect(Collectors.toList());
    }


}
