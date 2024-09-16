package com.example.demo.services.interfaces;

import com.example.demo.models.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart);

    public List<ShoppingCart> getAllShoppingCarts();

    public Optional<ShoppingCart> getShoppingCartById(Long id);

    public ShoppingCart updateShoppingCart(ShoppingCart updatedShoppingCart);

    public void deleteShoppingCartById(Long id);

    public void deleteAllShoppingCarts();

    public ShoppingCart addCourseToCart(Long cartId, Long userId);

    public double calculateTotalAmount(Long cartId);
}
