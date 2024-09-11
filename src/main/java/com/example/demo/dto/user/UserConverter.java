package com.example.demo.dto.user;

import com.example.demo.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    @Autowired
    ModelMapper modelMapper;

    public UserDTO userCartToDto (User user){
        return modelMapper.map (user, UserDTO.class);
    }
    public User dtoToUser (UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
