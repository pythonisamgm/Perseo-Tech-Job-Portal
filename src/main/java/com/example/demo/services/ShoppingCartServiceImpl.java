
package com.example.demo.services;

import com.example.demo.models.ShoppingCart;
import com.example.demo.repositories.IShoppingCartRepository;
import com.example.demo.services.interfaces.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private IShoppingCartRepository shoppingCartRepository;

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public List<ShoppingCart> getAllShoppingCarts() {
        try {
            return shoppingCartRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all shopping carts", e);
        }
    }

    public Optional<ShoppingCart> getShoppingCartById(Long id) {
        try {
            return shoppingCartRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving shopping cart by id", e);
        }
    }

    public ShoppingCart updateShoppingCart(ShoppingCart updatedShoppingCart) {
        Optional<ShoppingCart> existingShoppingCart = shoppingCartRepository.findById(updatedShoppingCart.getId());
        if (existingShoppingCart.isPresent()) {
            ShoppingCart cart = existingShoppingCart.get();
            cart.setUser(updatedShoppingCart.getUser());
            cart.setCourses(updatedShoppingCart.getCourses());
            return shoppingCartRepository.save(cart);
        }
        return null;
    }

    public void deleteShoppingCartById(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    public void deleteAllShoppingCarts() {
        shoppingCartRepository.deleteAll();
    }
}

