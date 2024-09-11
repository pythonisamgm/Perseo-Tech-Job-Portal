package com.example.demo.dto.user;

import com.example.demo.dto.shoppingCart.ShoppingCartDTO;
import com.example.demo.models.ShoppingCart;
import com.example.demo.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserConverter {
    @Autowired
    ModelMapper modelMapper;

    public UserDTO userCartToDto (User user){
        return modelMapper.map (user, UserDTO.class);
    }
    public User dtoToUser (ShoppingCartDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
