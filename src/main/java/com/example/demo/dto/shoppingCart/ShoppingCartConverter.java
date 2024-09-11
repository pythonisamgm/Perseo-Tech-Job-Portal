package com.example.demo.dto.shoppingCart;

import com.example.demo.dto.payment.PaymentDTO;
import com.example.demo.models.Payment;
import com.example.demo.models.ShoppingCart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
