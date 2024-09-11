package com.example.demo.dto.shoppingCart;

import com.example.demo.models.ShoppingCart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartConverter {
    @Autowired
    ModelMapper modelMapper;

    public ShoppingCartDTO shoppingCartToDto (ShoppingCart shoppingCart){
        return modelMapper.map (shoppingCart, ShoppingCartDTO.class);
    }
    public ShoppingCart dtoToShoppingCart (ShoppingCartDTO shoppingCartDTO){
        return modelMapper.map(shoppingCartDTO, ShoppingCart.class);
    }
}
